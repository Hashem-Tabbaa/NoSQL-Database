package com.database.atypon.DBMS.controller;

import com.database.atypon.DBMS.database_system.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String getDashboard(Model model){
        model.addAttribute("database", new Database());
        return "dashboard";
    }
}
