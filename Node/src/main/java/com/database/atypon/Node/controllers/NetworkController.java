package com.database.atypon.Node.controllers;

import com.database.atypon.Node.model.Network;
import com.database.atypon.Node.model.Node;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NetworkController {

    private final Network network;

    public NetworkController(Network network){
        this.network = network;
    }

    @PostMapping("/network/add/node")
    public String addNode(@RequestBody Node node){
        if(node.getName().equals("Node0"))
            return "Null";
        network.addNode(node);
        return "Node added";
    }
    @PostMapping("/network/add/nodes")
    public String setNodes(@RequestBody List<Node> nodes){
        for(Node node : nodes){
            System.out.print(node.getName() + "  ");
        }
        network.setNodes(nodes);
        return "Nodes set";
    }
    @PostMapping("/network/assign/self")
    public String assignSelf(@RequestBody Node node){
        network.assignSelf(node);
        return "Self assigned";
    }

    @GetMapping("/network/get/nodes")
    public List<Node> getNodes(){
        return network.getNodes();
    }

    @GetMapping("/user/network/get/self")
    public Node getSelf(){
        return network.getSelf();
    }

}
