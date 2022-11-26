package com.database.atypon.DBMS.database_system.connection;

import com.database.atypon.DBMS.database_system.node.Node;
import com.database.atypon.DBMS.model.User;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

public class ConnectionRequest {

    private static String boostStrappingNodeURL = "http://localhost:8079";

    public static String createNewUser(User user){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = boostStrappingNodeURL + "/createNewUser";
            String nodeURL = (restTemplate.postForObject(url, user, Node.class)).getURL();
            return nodeURL;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String login(User user, String nodeURL) throws Exception {
        if(user == null || nodeURL == null)
            throw new Exception("Invalid user or nodeURL");

        RestTemplate restTemplate = new RestTemplate();
        String url = nodeURL + "/login";
        LinkedHashMap<String, String> response = restTemplate.postForObject(url, user, LinkedHashMap.class);
        if(response.get("responseType").equals("ERROR"))
            throw new Exception(response.get("message"));
        return response.get("content");
    }
    public static String retrieveNodeURL(User user) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = boostStrappingNodeURL + "/getUserNode";
        try{
            String nodePort = (restTemplate.postForObject(url, user, String.class));
            return "http://localhost:" + nodePort;
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("User not found");
        }
    }

}

