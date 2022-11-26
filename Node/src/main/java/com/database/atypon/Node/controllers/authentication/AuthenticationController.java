package com.database.atypon.Node.controllers.authentication;

import com.database.atypon.Node.services.authentication.AuthenticationService;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.model.User;
import com.database.atypon.Node.utils.response.ResponseType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public Response login(@RequestBody User user)
            throws Exception {

        Response res = authenticationService.authenticateUser(user);
        if(res.getResponseType() == ResponseType.ERROR)
            return res;

        user = (User) res.getContent();
        String token = authenticationService.generateToken(user);
        res.setContent(token);
        return res;
    }
    @PostMapping("/logout")
    public Response logout(HttpServletRequest request, HttpServletResponse response) {

        String token;
        try{
            token = String.valueOf(WebUtils.getCookie(request, "token"));
        }catch (Exception e){
            return new Response(ResponseType.ERROR, "You are not logged in");
        }
        if (token == "null")
            return new Response(ResponseType.ERROR, "You are not logged in");
        Cookie cookie = authenticationService.buildTokenCookie(token);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new Response(ResponseType.SUCCESS, "Logout successfully");
    }
}
