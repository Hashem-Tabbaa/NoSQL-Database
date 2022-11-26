package com.database.atypon.Node.utils.response;

import org.springframework.stereotype.Component;

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
