package com.database.atypon.DBMS.database_system.read;

import com.database.atypon.DBMS.database_system.PathBuilder;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ReadRequest {


    public static JSONObject readAll(String databaseName, String schemaName, String token, String nodeURL) {
        RestTemplate restTemplate = new RestTemplate();
        String url = nodeURL + PathBuilder.buildReadAllDocumentsPath(databaseName, schemaName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        LinkedHashMap<String, String> response = restTemplate.postForObject(url, entity, LinkedHashMap.class);
        return new JSONObject(response.get("content"));

    }

    public static JSONObject readById(String databaseName, String schemaName, String id, String token, String nodeURL) {
        RestTemplate restTemplate = new RestTemplate();
        String url = nodeURL + PathBuilder.buildReadDocumentPath(databaseName, schemaName, id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        HttpEntity<String> request = new HttpEntity<>(headers);
        LinkedHashMap<String , Object> res =  restTemplate.postForObject(url, request, LinkedHashMap.class);
        return new JSONObject(res.get("content").toString());
    }
}
