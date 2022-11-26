package com.database.atypon.DBMS.database_system;

public class PathBuilder {

    public static String buildCreateDatabasePath(String databaseName){
        return "/admin/database/create?" + "databaseName=" + databaseName;
    }
    public static String buildCreateSchemaPath(String databaseName){
        return "/write/schema/new?" + "database=" + databaseName;
    }
    public static String buildCreateDocumentPath(String databaseName, String schemaName){
        return "/write/document/new?" + "database=" + databaseName + "&schema=" + schemaName;
    }
    public static String buildReadAllDocumentsPath(String databaseName, String schemaName){
        return "/user/read/all?" + "databaseName=" + databaseName + "&schemaName=" + schemaName;
    }
    public static String buildReadDocumentPath(String databaseName, String schemaName, String id){
        return "/user/read/document?" + "databaseName=" + databaseName + "&schemaName=" +
                schemaName + "&id=" + id;
    }

}
