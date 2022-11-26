package com.database.atypon.DBMS.database_system.read;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class ReadOperations {

    public static JSONObject readAllDocuments(String databaseName, String schemaName, String token, String nodeURL) {
        return ReadRequest.readAll(databaseName, schemaName, token, nodeURL);
    }

    public static JSONObject readDocumentById(String databaseName, String schemaName, String id,
                                              String token, String nodeURL) {
        return ReadRequest.readById(databaseName, schemaName, id, token, nodeURL);
    }

}
