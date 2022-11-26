package com.database.atypon.Node.model;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class Network {

    public static List<Node> nodes;
    public static Node self;

    public Network(){
        nodes = Collections.synchronizedList(new LinkedList<Node>());
    }

    public void addNode(Node node){
        nodes.add(node);
    }
    public void setNodes(List<Node> nodes){
        this.nodes = Collections.synchronizedList(nodes);
    }

    public List<Node> getNodes(){
        return nodes;
    }

    public void assignSelf(Node node) {
        this.self = node;
    }

    public static Node getSelf() {
        return self;
    }
}
