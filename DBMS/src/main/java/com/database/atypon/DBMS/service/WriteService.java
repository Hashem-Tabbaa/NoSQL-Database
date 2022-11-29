package com.database.atypon.DBMS.service;

import com.database.atypon.DBMS.database_system.connection.DatabaseOperations;
import com.database.atypon.DBMS.model.Schema;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Service
public class WriteService {
    public String createSchema(Schema schema, String token, String nodeURL)throws Exception{
        DatabaseOperations databaseOperations = new DatabaseOperations(nodeURL, token);
        JSONObject schemaJson = new JSONObject();

        schemaJson.put("schemaName", schema.getSchemaName());

        JSONObject temp = new JSONObject(schema.getSchema());
        System.out.println(temp.getString("Name"));
        schemaJson.put("schema", new JSONObject(schema.getSchema()));

        System.out.println("Schema: " + schemaJson.toString());
        return databaseOperations.createSchema(schema.getDatabaseName(), schema.getSchemaName(), schemaJson);
    }
}
