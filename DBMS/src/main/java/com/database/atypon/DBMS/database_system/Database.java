package com.database.atypon.DBMS.database_system;

public class Database {

    private String name;

    public Database(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}
