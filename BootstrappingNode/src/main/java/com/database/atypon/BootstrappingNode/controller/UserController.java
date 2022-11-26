package com.database.atypon.BootstrappingNode.controller;

import com.database.atypon.BootstrappingNode.model.Node;
import com.database.atypon.BootstrappingNode.model.User;
import com.database.atypon.BootstrappingNode.utils.UsersLoadBalancer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserController {

    @PostMapping("/createNewUser")
    public Node createNewUser(@RequestBody User user){
        if(user == null)
            return null;
        if(user.getPassword() == null || user.getPassword().isEmpty())
            return null;
        if(user.getUsername() == null || user.getUsername().isEmpty())
            return null;

        RestTemplate restTemplate = new RestTemplate();
        Node node = UsersLoadBalancer.getUserNode(3);
        String url = node.getURL() + "/admin/user/add";
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "admin");
        HttpEntity request = new HttpEntity(user, headers);
        try{
            restTemplate.postForObject(url, request, List.class);
            return node;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @PostMapping("/getUserNode")
    public String getUserNode(@RequestBody User user){
        if(user == null)
            return null;
        if(user.getPassword() == null || user.getPassword().isEmpty())
            return null;
        if(user.getUsername() == null || user.getUsername().isEmpty())
            return null;
        return UsersLoadBalancer.getCurrentUserNode(3).getPort();
    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }

}
