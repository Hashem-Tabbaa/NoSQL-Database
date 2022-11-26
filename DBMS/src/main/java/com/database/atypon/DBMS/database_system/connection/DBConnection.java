package com.database.atypon.DBMS.database_system.connection;

import com.database.atypon.DBMS.model.User;

public class DBConnection {

    private User user;

    public DBConnection(User user){
        this.user = user;
    }

    public String getNodeURL() throws Exception {
        return ConnectionRequest.retrieveNodeURL(user);
    }

    public String login(User user, String nodeURL) throws Exception {
        return ConnectionRequest.login(user, nodeURL);
    }
}
