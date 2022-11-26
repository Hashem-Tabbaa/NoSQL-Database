package com.database.atypon.Node.services.write;

import com.database.atypon.Node.model.Network;
import com.database.atypon.Node.model.Node;
import com.database.atypon.Node.operations.write.WriteOperation;
import com.database.atypon.Node.utils.AffinityLoadBalancer;
import com.database.atypon.Node.utils.PathBuilder;
import com.database.atypon.Node.utils.file_operations.fileWriter.FileWriter;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class WriteService {

    private final WriteOperation writeOperation;

    public WriteService(WriteOperation writeOperation) {
        this.writeOperation = writeOperation;
    }

    public Response createSchema(String database, HashMap<String, Object> schema) {
            JSONObject schemaJSON = new JSONObject(schema);
            String schemaName = schemaJSON.get("schemaName").toString();

            // Assign the node affinity to the schema using the load balancer
            String nodeAffinity = AffinityLoadBalancer.assignAffinity(database, schemaName);

            // write the node affinity in the affinities directory
            writeNodeAffinity(database, schemaName, nodeAffinity);

            JSONObject schemaDetails = schemaJSON.getJSONObject("schema");
            return writeOperation.createSchema(database, schemaName, schemaDetails);
    }

    public Response createDocument(String database, String schema, HashMap<String, Object> document) {
        try{
            JSONObject documentJSON = new JSONObject(document);
            return writeOperation.createDocument(database, schema, documentJSON);
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }

    public List<Response> broadcastSchema(String database, HashMap<String, Object> schema) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4,4, 2,TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        Vector<Response> res = new Vector<>();
        for (Node node : Network.nodes) {
            executor.execute(() -> {
                synchronized (res) {
                    res.add(node.createSchema(database, schema));
                }
            });
        }
        return res;
    }

    public List<Response> broadcastDocument(String database, String schema, HashMap<String, Object> document) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4,4, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        List<Response> res = new Vector<>();
        for (Node node : Network.nodes) {
            executor.execute(() -> {
                synchronized (res) {
                    res.add(node.createDocument(database, schema, document));
                }
            });
        }
        return res;
    }

    private void writeNodeAffinity(String database, String schemaName, String nodeAffinity) {
        try{
            String schemaAffinityPath = PathBuilder.getPathToAffinity(database, schemaName);
            File schemaAffinityFile = new File(schemaAffinityPath);
            schemaAffinityFile.createNewFile();

            JSONObject schemaAffinity = new JSONObject();
            schemaAffinity.put("Node", nodeAffinity);

            FileWriter fileWriter = new FileWriter(schemaAffinityFile, schemaAffinity.toString());
            fileWriter.write();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
