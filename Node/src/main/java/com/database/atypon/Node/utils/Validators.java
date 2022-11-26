package com.database.atypon.Node.utils;

import com.database.atypon.Node.utils.file_operations.fileReader.FileReader;
import org.json.JSONObject;

import java.io.File;

public class Validators {
    public static boolean validateSchema(JSONObject schemaDetails, String schemaName) throws Exception {
        if (schemaName == null)
            throw new Exception("Schema name is null");
        if (schemaDetails == null || schemaDetails.length() == 0)
            throw new Exception("Schema is empty");
        for (String key : schemaDetails.keySet()) {
            if (key == null || schemaDetails.get(key) == null)
                throw new Exception("Schema details are null");
            //types should only be String, Integer, Double, Boolean
            if (!schemaDetails.getString(key).equals("String") &&
                    !schemaDetails.getString(key).equals("Integer") &&
                    !schemaDetails.getString(key).equals("Double") &&
                    !schemaDetails.getString(key).equals("Boolean"))
                throw new Exception("Invalid type for key: " + key);
        }
        return true;
    }

    public static boolean validateDocument(JSONObject documentJSON,
                                        String schema,
                                        String database) throws Exception {

        FileReader fileReader = new FileReader
                (new File(PathBuilder.getPathToSchema(database, schema)));
        try{
            fileReader.read();
        }catch (Exception e){
            throw new Exception("Schema doesn't exist");
        }
        JSONObject schemaJSON = new JSONObject(fileReader.getContent());
        JSONObject schemaDetails = schemaJSON.getJSONObject("schema");
        for (String key : schemaDetails.keySet()) {
            String type = "java.lang." + schemaDetails.getString(key);

            if (!documentJSON.has(key))
                throw new Exception("Document doesn't have key: " + key);

            if (documentJSON.get(key) == null)
                throw new Exception("Document value is null for key: " + key);

            if (!documentJSON.get(key).getClass().getName().equals(type)) {
                throw new Exception("Document value type is not the same as schema for key: " + key +
                        " document value type: " + documentJSON.get(key).getClass().getName() +
                        " schema value type: " + schemaDetails.get(key));
            }
        }
        return true;
    }
}
