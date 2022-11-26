package com.database.atypon.DBMS.service;

import com.database.atypon.DBMS.database_system.connection.DBConnection;
import com.database.atypon.DBMS.database_system.connection.DatabaseOperations;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public String createDatabase(String databaseName, String token, String nodeURL) throws Exception {
        DatabaseOperations databaseOperations = new DatabaseOperations(nodeURL, token);
        return databaseOperations.createDatabase(databaseName);
    }

}
