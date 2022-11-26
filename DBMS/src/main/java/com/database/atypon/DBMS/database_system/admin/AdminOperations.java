package com.database.atypon.DBMS.database_system.admin;

import com.database.atypon.DBMS.database_system.connection.ConnectionRequest;
import com.database.atypon.DBMS.model.User;

public class AdminOperations {

    public static String createDatabase(String database, String token, String nodeURL){
        return AdminRequest.createDatabase(database, token, nodeURL);
    }

    public static String createNewUser(User user){
        return ConnectionRequest.createNewUser(user);
    }
}
