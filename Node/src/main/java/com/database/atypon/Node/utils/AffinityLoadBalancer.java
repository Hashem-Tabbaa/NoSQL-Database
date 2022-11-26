package com.database.atypon.Node.utils;

import com.database.atypon.Node.model.Network;
import com.database.atypon.Node.model.Node;
import com.database.atypon.Node.utils.file_operations.fileReader.FileReader;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.json.JSONObject;

import java.io.File;

public class AffinityLoadBalancer {

    public static String assignAffinity(String database, String schema){
        String pathToAffinity = PathBuilder.getPathToAllAffinities(database);

        File folder = new File(pathToAffinity);
        int numberOfNodes = Network.nodes.size()+1;
        int numberOfSchemas = 0;
        if(folder.listFiles() != null)
            numberOfSchemas = folder.listFiles().length;

        int affinity = numberOfSchemas % numberOfNodes;

        return "Node" + affinity;
    }

    public static Response checkAffinity(String database, String schema){
        String pathToAffinity = PathBuilder.getPathToAffinity(database, schema);

        File file = new File(pathToAffinity);

        if(!file.exists())
            return new Response(ResponseType.ERROR, "Affinity file does not exist");

        FileReader reader = new FileReader(new File(pathToAffinity));
        reader.read();
        JSONObject affinityJSON = new JSONObject(reader.getContent());
        String node = affinityJSON.getString("Node");

        if(Network.getSelf().getName().equals(node))
            return new Response(ResponseType.SUCCESS, "Affinity is correct");
        return new Response(ResponseType.ERROR, "Affinity is incorrect", Node.nodeBuilder(node));
    }
}
