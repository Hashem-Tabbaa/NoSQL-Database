package com.database.atypon.DBMS.database_system.node;

public class Node {

    private String name;
    private String port;

    public Node(){

    }

    public Node(String name, String port) {
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }
    public String getPort(){
        return port;
    }
    public String getURL(){
        return "http://"+name+":"+port;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
