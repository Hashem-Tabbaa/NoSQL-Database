package com.database.atypon.DBMS.database_system;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Database {

    private String name;

    public Database(String name){
        this.name = name;
    }
    public Database(){

    }
}
