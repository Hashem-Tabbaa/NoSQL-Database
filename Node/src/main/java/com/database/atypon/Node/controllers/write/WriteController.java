package com.database.atypon.Node.controllers.write;

import com.database.atypon.Node.model.Node;
import com.database.atypon.Node.services.authentication.AuthenticationService;
import com.database.atypon.Node.services.write.WriteService;
import com.database.atypon.Node.utils.AffinityLoadBalancer;
import com.database.atypon.Node.utils.Token;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

@RestController
@RequestMapping("/write")
public class WriteController {

    private final WriteService writeService;
    private final AuthenticationService authenticationService;

    public WriteController(WriteService writeService, AuthenticationService authenticationService) {
        this.writeService = writeService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/schema/new", produces = "application/json")
    public Vector<Response> createSchema(@RequestParam String database,
                                       @RequestBody HashMap<String, Object> schema,
                                       @RequestHeader("authorization") String token) {

        if(schema == null || schema.isEmpty())
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Schema is empty")));
        if(database == null || database.isEmpty())
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Database name is empty")));

        if(!authenticationService.isUserToken(token))
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Invalid token")));

        Vector<Response> responses = new Vector<>();
        responses.add(writeService.createSchema(database, schema));

        if(token.equals(Token.INTERNAL))
            return responses;

        responses.addAll(writeService.broadcastSchema(database, schema));

        return responses;
    }

    @PostMapping(value = "/document/new", produces = "application/json")
    public Vector<Response> createDocument(@RequestParam String database,
                                         @RequestParam String schema,
                                         @RequestBody HashMap<String, Object> document,
                                         @RequestHeader("authorization") String token) {
        if(document == null || document.isEmpty())
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Document is empty")));
        if(database == null || database.isEmpty())
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Database name is empty")));
        if(schema == null || schema.isEmpty())
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Schema name is empty")));

        System.out.println("Write document from " + token);

        if(!authenticationService.isUserToken(token))
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Invalid token")));

        if(authenticationService.isInternalToken(token))
            return new Vector<>(List.of(writeService.createDocument(database, schema, document)));

        //forward the request to another node if the node affinity is not the current node
        Response affinityResponse = AffinityLoadBalancer.checkAffinity(database, schema);
        Vector<Response> responses = new Vector<>();
        if(affinityResponse.getResponseType() == ResponseType.ERROR){
            Node node = (Node) affinityResponse.getContent();
            String url = node.getURL() + "/write/document/new?database=" + database + "&schema=" + schema;
            Thread t = new Thread(()->{
                synchronized (responses) {
                    responses.addAll(forwardRequest(url, document, token));
                }
            });
            t.start();
            try{
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return responses;
        }
        responses.add(writeService.createDocument(database, schema, document));

        if(responses.get(0).getResponseType() == ResponseType.SUCCESS)
            responses.addAll(writeService.broadcastDocument(database, schema, document));

        return responses;
    }

    private Vector<Response> forwardRequest(String url, HashMap<String, Object> document, String token) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("authorization", token);
            HttpEntity request = new HttpEntity(document, headers);
            RestTemplate restTemplate = new RestTemplate();

            Vector<LinkedHashMap> returned = restTemplate.postForObject(url, request, Vector.class);
            Vector<Response> responses = new Vector<>();

            for(LinkedHashMap response : returned)
                responses.add(new Response(ResponseType.valueOf((String) response.get("responseType")),
                        (String) response.get("message")));

            return responses;

        }catch (Exception e){
            return new Vector<>(List.of(new Response(ResponseType.ERROR, "Error forwarding request", e.getMessage())));
        }
    }
}