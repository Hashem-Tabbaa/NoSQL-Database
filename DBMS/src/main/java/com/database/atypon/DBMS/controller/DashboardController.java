package com.database.atypon.DBMS.controller;

import com.database.atypon.DBMS.database_system.Database;
import com.database.atypon.DBMS.model.Schema;
import com.database.atypon.DBMS.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String getDashboard(Model model){
        model.addAttribute("database", new Database());
        model.addAttribute("user", new User());
        model.addAttribute("schema", new Schema());
        return "dashboard";
    }
}
