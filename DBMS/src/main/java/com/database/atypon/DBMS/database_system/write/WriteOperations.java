package com.database.atypon.DBMS.database_system.write;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public class WriteOperations {

    public static String createSchema(String databaseName, String schemaName,
                                      JSONObject schema,
                                      String token, String nodeURL) {

        JSONObject schemaJson = new JSONObject();
        schemaJson.put("schemaName", schemaName);
        schemaJson.put("schema", schema);
        return WriteRequest.createSchema(databaseName,schemaJson, token, nodeURL);
    }
    public static String createDocument(String databaseName, String schemaName,
                                        HashMap<Object, Object> document,
                                        String token, String nodeURL) {

        JSONObject documentJson = new JSONObject(document);

        return WriteRequest.createDocument(databaseName, schemaName, documentJson, token, nodeURL);
    }
}
