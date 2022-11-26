package com.database.atypon.BootstrappingNode.utils;

import com.database.atypon.BootstrappingNode.model.Node;
import com.database.atypon.BootstrappingNode.model.User;

public class UsersLoadBalancer {

    private static int usersCount = 0;

    public static Node getUserNode(int numberOfnodes){
        int nodeNumber = usersCount++ % numberOfnodes;
        return new Node("node"+nodeNumber, "8080");
    }


    public static Node getCurrentUserNode(int numberOfnodes) {
        int nodeNumber = usersCount % numberOfnodes;
        return new Node("Node"+nodeNumber, "8080");
    }
}
