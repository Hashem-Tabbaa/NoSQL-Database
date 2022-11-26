package com.database.atypon.DBMS.database_system.admin;

import com.database.atypon.DBMS.database_system.PathBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Vector;

public class AdminRequest {
    public static String createDatabase(String database, String token, String nodeURL) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = nodeURL + PathBuilder.buildCreateDatabasePath(database);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity request = new HttpEntity(headers);

        Vector<LinkedHashMap> responses = restTemplate.postForObject(url, request, Vector.class);
        LinkedHashMap response = responses.get(0);
        if(response.get("responseType").equals("ERROR"))
            throw new Exception(response.get("message").toString());
        return response.get("message").toString();
    }
}
