package com.database.atypon.DBMS.database_system.write;

import com.database.atypon.DBMS.database_system.PathBuilder;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class WriteRequest {


    public static String createSchema(String databaseName, JSONObject schema,
                                      String token, String nodeURL) {
        RestTemplate restTemplate = new RestTemplate();

        String url = nodeURL + PathBuilder.buildCreateSchemaPath(databaseName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(schema.toString(), headers);

        return restTemplate.postForObject(url, request, String.class);
    }

    public static String createDocument(String databaseName, String schemaName,
                                        JSONObject document, String token, String nodeURL) {

        RestTemplate restTemplate = new RestTemplate();

        String url = nodeURL + PathBuilder.buildCreateDocumentPath(databaseName, schemaName);
        System.out.println("Document : " + document.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(document.toString(), headers);

        return restTemplate.postForObject(url, request, String.class);
    }
}
