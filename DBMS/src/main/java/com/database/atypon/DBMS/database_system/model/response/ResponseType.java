package com.database.atypon.DBMS.database_system.model.response;

public enum ResponseType {

    SUCCESS("Successful"), ERROR("Error");

    private String message;

    ResponseType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
