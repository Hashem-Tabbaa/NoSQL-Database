package com.database.atypon.BootstrappingNode.model;

public class Node {

    private final String name;
    private final String port;

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
    public Node nodeBuilder(String name, String port){
        return new Node(name, port);
    }
}
