package com.database.atypon.DBMS.database_system.model.response;

public class Response {

    private ResponseType responseType;
    private String message;
    private Object content;

    public Response(ResponseType responseType, String message, Object content) {
        this.responseType = responseType;
        this.message = message;
        this.content = content;
    }

    public Response(ResponseType responseType, String message) {
        this.responseType = responseType;
        this.message = message;
    }
    public Response(){
        this.responseType = ResponseType.SUCCESS;
        this.message = "Default";
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public boolean isError(){
        return responseType == ResponseType.ERROR;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseType=" + responseType +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}
