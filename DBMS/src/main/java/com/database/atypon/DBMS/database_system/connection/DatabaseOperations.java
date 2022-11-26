package com.database.atypon.DBMS.database_system.connection;

import com.database.atypon.DBMS.database_system.admin.AdminOperations;
import com.database.atypon.DBMS.database_system.read.ReadOperations;
import com.database.atypon.DBMS.database_system.write.WriteOperations;
import com.database.atypon.DBMS.model.User;
import org.json.JSONObject;

import java.util.HashMap;

public class DatabaseOperations {

    private final String nodeURL;
    private User user;
    private String token;

    public DatabaseOperations(User user, String nodeURL) throws Exception {
        this.nodeURL = nodeURL;
        this.user = user;
    }

    // This method is used to log in to the DB system by retrieving the token from the node
    public String login() throws Exception {
        this.token = ConnectionRequest.login(user, nodeURL);
        return this.token;
    }
    public String createDatabase(String databaseName) throws Exception {
        if(token == null)
            throw new Exception("User not logged in");
        System.out.println("User Token: " + token);
        return AdminOperations.createDatabase(databaseName, token, nodeURL);
    }
    public String createNewUser(User user) throws Exception {
        if(token == null)
            throw new Exception("User not logged in");
        return AdminOperations.createNewUser(user);
    }
    public String createSchema(String databaseName, String schemaName,
                               HashMap<String, String> columns) throws Exception {
        if(token == null)
            throw new Exception("User not logged in");
        return WriteOperations.createSchema(databaseName, schemaName, columns, token, nodeURL);
    }
    public String createDocument(String databaseName, String schemaName,
                                 HashMap<Object, Object> document) throws Exception {
        if(token == null)
            throw new Exception("User not logged in");
        return WriteOperations.createDocument(databaseName, schemaName, document, token, nodeURL);
    }
    public JSONObject readAllDocuments(String databaseName, String schemaName) throws Exception {
        if(token == null)
            throw new Exception("User not logged in");
        return ReadOperations.readAllDocuments(databaseName, schemaName, token, nodeURL);
    }
    public JSONObject readDocumentById(String databaseName, String schemaName, String id) throws Exception {
        if(token == null)
            throw new Exception("User not logged in");
        return ReadOperations.readDocumentById(databaseName, schemaName, id, token, nodeURL);
    }
}
