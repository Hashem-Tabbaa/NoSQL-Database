package com.database.atypon.Node.controllers.authentication;

import com.database.atypon.Node.model.User;
import com.database.atypon.Node.services.authentication.AuthenticationService;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
