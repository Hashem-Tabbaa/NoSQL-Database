package com.database.atypon.Node.model;

import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

public class Node {

    private final String name;
    private final String port;

    public Node(String name, String port) {
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }
    public String getPort(){
        return port;
    }
    public String getURL(){
        return "http://"+name+":"+port;
    }

    public Response addUser(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "internal");
        HttpEntity request = new HttpEntity(user, headers);
        try{
            Vector<LinkedHashMap> response = restTemplate.postForObject(getURL()+"/admin/user/add",
                    request, Vector.class);
            return new Response(ResponseType.valueOf((String) response.get(0).get("responseType")),
            (String) response.get(0).get("message"));
        }catch(Exception e){
            return new Response(ResponseType.ERROR, this.name + ": " + e.getMessage());
        }
    }

    public Response addDatabase(String databaseName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "internal");
        HttpEntity entity = new HttpEntity(headers);
        try{
            Vector<LinkedHashMap<String, String>> res = restTemplate.postForObject(this.getURL()+
                    "/admin/database/create?databaseName="+databaseName, entity, Vector.class);
            return new Response(ResponseType.valueOf(res.get(0).get("responseType")), res.get(0).get("message"));
        }catch (Exception e){
            return new Response(ResponseType.ERROR, this.name + ": " + e.getMessage());
        }
    }

    public Response createSchema(String database, HashMap<String, Object> schema) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "internal");
        HttpEntity entity = new HttpEntity(schema, headers);
        try{
            Vector<LinkedHashMap<String,String>> response = restTemplate.postForObject(getURL()+
                    "/write/schema/new?database="+database, entity, Vector.class);
            return new Response(ResponseType.valueOf(response.get(0).get("responseType")), response.get(0).get("message"));
        }catch (Exception e){
            return new Response(ResponseType.ERROR, this.name + ": " + e.getMessage());
        }
    }
    public Response createDocument(String database, String schema, HashMap<String, Object> document) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "internal");
        HttpEntity entity = new HttpEntity(document, headers);
        try{
            Vector<LinkedHashMap<String,String>> response = restTemplate.postForObject(getURL()+
                    "/write/document/new?database="+database+"&schema="+schema,
                    entity, Vector.class);
            return new Response(ResponseType.valueOf(response.get(0).get("responseType")), response.get(0).get("message"));
        }catch (Exception e){
            return new Response(ResponseType.ERROR, this.name + ": " + e.getMessage());
        }
    }
    public static Node nodeBuilder(String name){
        return new Node(name, "8080");
    }
    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
