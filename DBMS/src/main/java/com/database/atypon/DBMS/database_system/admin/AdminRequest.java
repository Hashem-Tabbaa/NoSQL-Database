package com.database.atypon.DBMS.database_system.admin;

import com.database.atypon.DBMS.database_system.PathBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class AdminRequest {
    public static String createDatabase(String database, String token, String nodeURL) {
        RestTemplate restTemplate = new RestTemplate();

        String url = nodeURL + PathBuilder.buildCreateDatabasePath(database);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity request = new HttpEntity(headers);

        return restTemplate.postForObject(url, request, String.class);
    }
}
