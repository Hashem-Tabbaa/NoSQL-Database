package com.database.atypon.DBMS.controller;

import com.database.atypon.DBMS.model.User;
import com.database.atypon.DBMS.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class LoginController {

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public String login(Model model){
        System.out.println("Login Page");
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(User user, HttpServletRequest request) throws Exception {
        try{
            System.out.println(user.getPassword() + " " + user.getUsername());
            String token = authenticationService.authenticateUser(user);
            System.out.println(token);
//             add token to session
            WebUtils.setSessionAttribute(request, "token", token);
            return "redirect:/dashboard";
        }catch (Exception e){
            return "login";
        }
    }

}
