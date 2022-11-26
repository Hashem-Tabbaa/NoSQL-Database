package com.database.atypon.Node.operations.write;

import com.database.atypon.Node.utils.PathBuilder;
import com.database.atypon.Node.utils.Validators;
import com.database.atypon.Node.utils.file_operations.fileReader.FileReader;
import com.database.atypon.Node.utils.file_operations.fileWriter.FileWriter;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class WriteOperation {


    public Response createSchema(String database, String schemaName, JSONObject schemaDetails) {
        if (!new File(PathBuilder.getPathToDatabase(database)).exists())
            return new Response(ResponseType.ERROR, "Database doesn't exist");
        try {
            Validators.validateSchema(schemaDetails, schemaName);
            synchronized (this) {
                JSONObject finalSchema = new JSONObject();
                JSONObject infoObject = new JSONObject();

                infoObject.put("schemaName", schemaName);
                infoObject.put("nextId", 0);

                finalSchema.put("info", infoObject);
                finalSchema.put("schema", schemaDetails);

                String pahToSchema = PathBuilder.getPathToSchema(database, schemaName);
                File schemaFile = new File(pahToSchema);

                if (schemaFile.exists())
                    return new Response(ResponseType.ERROR, "Schema already exists");

                schemaFile.createNewFile();

                //create directory to the data of the schema
                File schemaData = new File(PathBuilder.getPathToAllDocuments(database, schemaName));
                try {
                    schemaData.mkdir();
                } catch (Exception e) {
                    return new Response(ResponseType.ERROR, "Error creating schema data directory");
                }
                return new FileWriter(schemaFile, finalSchema.toString()).write();
            }
        } catch (Exception e) {
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }

    public Response createDocument(String database, String schema, JSONObject documentJSON) {
        try {
            Validators.validateDocument(documentJSON, schema, database);
        } catch (Exception e) {
            return new Response(ResponseType.ERROR, e.getMessage());
        }
        synchronized (this) {
            try {
                String pathToSchema = PathBuilder.getPathToSchema(database, schema);
                File schemaFile = new File(pathToSchema);
                FileReader fileReader = new FileReader(schemaFile);
                fileReader.read();

                JSONObject schemaJSON = new JSONObject(fileReader.getContent());
                JSONObject infoObject = schemaJSON.getJSONObject("info");
                int nextId = infoObject.getInt("nextId");

                updateSchemaInfo(database, schema, nextId + 1);

                String pathToDocument = PathBuilder.getPathToDocument(database, schema,
                        String.valueOf(nextId));
                File documentFile = new File(pathToDocument);
                documentFile.createNewFile();
                FileWriter fileWriter = new FileWriter(documentFile, documentJSON.toString());
                fileWriter.write();
                return new Response(ResponseType.SUCCESS, "Document created successfully");
            } catch (Exception e) {
                return new Response(ResponseType.ERROR, e.getMessage());
            }

        }
    }

    private void updateSchemaInfo(String database, String schema, int i) throws Exception {
        try{
            String pathToSchema = PathBuilder.getPathToSchema(database, schema);
            File schemaFile = new File(pathToSchema);
            FileReader fileReader = new FileReader(schemaFile);
            fileReader.read();

            JSONObject schemaJSON = new JSONObject(fileReader.getContent());
            JSONObject infoObject = schemaJSON.getJSONObject("info");
            infoObject.put("nextId", i);

            FileWriter fileWriter = new FileWriter(schemaFile, schemaJSON.toString());
            fileWriter.write();
        }catch (Exception e){
            throw new Exception("Error updating schema info");
        }

    }
}
