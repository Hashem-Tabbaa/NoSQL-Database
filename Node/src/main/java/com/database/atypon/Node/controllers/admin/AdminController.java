package com.database.atypon.Node.controllers.admin;

import com.database.atypon.Node.model.User;
import com.database.atypon.Node.services.admin.AdminService;
import com.database.atypon.Node.services.authentication.AuthenticationService;
import com.database.atypon.Node.utils.Token;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AuthenticationService authenticationService;

    public AdminController(AdminService adminService, AuthenticationService authenticationService) {
        this.adminService = adminService;
        this.authenticationService = authenticationService;
    }
    @RequestMapping("/user/add")
    public Vector<Response> addUser(@RequestBody User user, @RequestHeader("authorization") String token){

        if(token == null)
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "You are not logged in")));

        if(!authenticationService.isAdminToken(token))
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "You are not an admin")));

        Vector<Response> responses = new Vector<>(List.of(adminService.addUser(user)));

        if(!token.equals(Token.ADMIN))
            return responses;

        responses.addAll(adminService.broadcastUser(user));
        return responses;
    }

    @PostMapping(value = "/database/create", produces = "application/json")
    public Vector<Response> createDatabase(@RequestParam String databaseName,
                                           @RequestHeader("authorization") String token) throws Exception {

        if(databaseName == null || databaseName.isEmpty())
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Database name is empty")));

        if(!authenticationService.isAdminToken(token))
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Invalid token")));

        Vector<Response> responses = new Vector<>(List.of(adminService.createDatabase(databaseName)));

        if(!token.equals(Token.ADMIN))
            return responses;

        responses.addAll(adminService.broadcastDatabase(databaseName));
        return responses;
    }
}
