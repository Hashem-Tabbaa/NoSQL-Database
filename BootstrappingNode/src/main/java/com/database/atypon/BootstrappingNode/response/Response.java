package com.database.atypon.BootstrappingNode.response;

import org.json.JSONObject;

public class Response {

    private ResponseType responseType;
    private String message;
    private Object content;

    public Response() {
    }

    public Response(ResponseType responseType, String message, Object content) {
        this.responseType = responseType;
        this.message = message;
        this.content = content;
    }

    public Response(ResponseType responseType, String message) {
        this.responseType = responseType;
        this.message = message;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("responseType", responseType);
        jsonObject.put("message", message);
        return jsonObject;
    }
    public boolean isSuccessful(){
        return responseType == ResponseType.SUCCESS;
    }
    public boolean isError(){
        return responseType == ResponseType.ERROR;
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
