package com.database.atypon.Node.utils;

public class PathBuilder {

    private static final String INITIAL_PATH = "./data/";

    public static String getInfoPath(){
        return INITIAL_PATH + "info.json";
    }

    public static String getPathToDocument(String databaseName, String collectionName, String id){
        return INITIAL_PATH + databaseName + "/" + collectionName + "-records/" + id + ".json";
    }

    public static String getPathToAllDocuments(String databaseName, String schemaName){
        return INITIAL_PATH + databaseName + "/" + schemaName + "-records/";
    }

    public static String getPathToDatabase(String databaseName){
        return INITIAL_PATH + databaseName;
    }

    public static String getPathToSchema(String databaseName, String schemaName){
        return INITIAL_PATH + databaseName + "/schemas/" + schemaName + ".json";
    }

    public static String getPathToAllAffinities(String databaseName){
        return INITIAL_PATH + databaseName + "/affinities/";
    }

    public static String getPathToAffinity(String databaseName, String schemaName){
        return INITIAL_PATH + databaseName + "/affinities/" + schemaName + ".json";
    }

    public static String getPathToAllSchemas(String databaseName){
        return INITIAL_PATH + databaseName + "/schemas/";
    }

    public static String getPathToMainInfo(){
        return INITIAL_PATH + "/info.json";
    }

    public static String getRootPath(){
        return INITIAL_PATH;
    }
}
