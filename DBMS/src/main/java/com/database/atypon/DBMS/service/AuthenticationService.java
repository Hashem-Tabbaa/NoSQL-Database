package com.database.atypon.DBMS.service;

import com.database.atypon.DBMS.database_system.connection.DBConnection;
import com.database.atypon.DBMS.database_system.connection.DatabaseOperations;
import com.database.atypon.DBMS.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public String authenticateUser(User user) throws Exception {
        DBConnection dbConnection = new DBConnection(user);
        String nodeURL = dbConnection.getNodeURL();
        DatabaseOperations databaseOperations = new DatabaseOperations(user, nodeURL);
        return databaseOperations.login();
    }
}
