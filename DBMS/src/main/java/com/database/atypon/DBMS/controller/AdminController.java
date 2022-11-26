package com.database.atypon.DBMS.controller;

import com.database.atypon.DBMS.database_system.Database;
import com.database.atypon.DBMS.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/createDatabase")
    public String createDatabase(Database database, HttpServletRequest request, Model model){
        String token = (String) request.getSession().getAttribute("token");
        String nodeURL = (String) request.getSession().getAttribute("nodeURL");
        try{
            adminService.createDatabase(database.getName(), token, nodeURL);
            return "redirect:/dashboard";
        }catch (Exception e){
            System.out.println(e.getMessage());
            model.addAttribute("databaseError", e.getMessage());
            return "redirect:/dashboard";
        }

    }

}
