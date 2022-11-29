package com.database.atypon.DBMS.controller;

import com.database.atypon.DBMS.model.Schema;
import com.database.atypon.DBMS.service.WriteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class WriteController {

    private final WriteService writeService;

    @PostMapping("/createSchema")
    public String createSchema(Schema schema, HttpServletRequest request){
        String token = (String) request.getSession().getAttribute("token");
        String nodeURL = (String) request.getSession().getAttribute("nodeURL");
        System.out.println(schema.getSchema());
        try{
            System.out.println(writeService.createSchema(schema, token, nodeURL));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/dashboard";
    }

}
