package com.database.atypon.Node.operations.admin;

import com.database.atypon.Node.model.User;
import com.database.atypon.Node.utils.PathBuilder;
import com.database.atypon.Node.utils.directory.DirectoryManager;
import com.database.atypon.Node.utils.file_operations.fileReader.FileReader;
import com.database.atypon.Node.utils.file_operations.fileUpdater.FileUpdater;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AdminOperations {

    public Response createDatabase(String databaseName) {
        // 1. new directory for the database in the databases directory
        // 2. directory for the schemas inside the database directory
        // 3. new file for the database info inside the database directory
        // 4. new directory for schemas affinities inside the database directory
        try {
            String pathToDatabase = PathBuilder.getPathToDatabase(databaseName);
            String pathToSchemas = PathBuilder.getPathToAllSchemas(databaseName);

            synchronized (this) {
                DirectoryManager databaseDirectory = new DirectoryManager(new File(pathToDatabase));
                DirectoryManager schemasDirectory = new DirectoryManager(new File(pathToSchemas));
                DirectoryManager affinitiesDirectory = new DirectoryManager(new File(PathBuilder.getPathToAllAffinities(databaseName)));

                databaseDirectory.createDirectory();
                schemasDirectory.createDirectory();
                affinitiesDirectory.createDirectory();

                JSONObject info = getInfo();
                JSONArray databases = info.getJSONArray("databases");
                databases.put(databaseName);
                info.put("databases", databases);

                FileUpdater fileUpdater = new FileUpdater(PathBuilder.getRootPath(), "info"
                        , info.toString(), ".json");
                fileUpdater.updateFile();
                return new Response(ResponseType.SUCCESS, "Database created successfully");
            }
        }catch (Exception e){
            return (new Response(ResponseType.ERROR, "Failed to create database"));
        }
    }

    public Response addUser(User user) {
        File file = new File(PathBuilder.getInfoPath());
        try{
            //add user to json file
            JSONObject obj = new JSONObject();
            obj.put("username", user.getUsername());
            obj.put("password", user.getPassword());
            obj.put("role", user.getRole());

            JSONObject info = getInfo();
            JSONObject users = info.getJSONObject("users");
            users.put(user.getUsername(), obj);
            FileUpdater fileUpdater = new FileUpdater(PathBuilder.getRootPath(), "info"
                    , info.toString(), ".json");
            fileUpdater.updateFile();
        }catch (Exception e){
            return (new Response(ResponseType.ERROR, "Failed to add user"));
        }
        return (new Response(ResponseType.SUCCESS, "User added successfully"));
    }

    private JSONObject getInfo(){
        String pathToMainInfo = PathBuilder.getPathToMainInfo();
        FileReader fileReader = new FileReader(new File(pathToMainInfo));
        fileReader.read();
        if(fileReader.getContent().isEmpty())
            return new JSONObject();

        return new JSONObject(fileReader.getContent());
    }

}
