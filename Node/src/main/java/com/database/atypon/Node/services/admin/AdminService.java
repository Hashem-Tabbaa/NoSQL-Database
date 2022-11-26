package com.database.atypon.Node.services.admin;

import com.database.atypon.Node.model.Network;
import com.database.atypon.Node.model.Node;
import com.database.atypon.Node.model.User;
import com.database.atypon.Node.operations.admin.AdminOperations;
import com.database.atypon.Node.utils.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class AdminService {

    private final AdminOperations adminOperations;

    public AdminService(AdminOperations adminOperations) {
        this.adminOperations = adminOperations;
    }

    public Response createDatabase(String databaseName) throws Exception {
        return adminOperations.createDatabase(databaseName);
    }
    public Response addUser(User user) {
        return adminOperations.addUser(user);
    }

    public List<Response> broadcastUser(User user) {
        List<Response> res = new Vector<>();
        for (Node node : Network.nodes) {
            res.add(node.addUser(user));
        }
        return res;
    }
    public List<Response> broadcastDatabase(String databaseName){
        // use thread pool to broadcast
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4,4, 2,TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        Vector<Response> res = new Vector<>();
        for (Node node : Network.nodes) {
            executor.execute(() -> {
                synchronized (res) {
                    res.add(node.addDatabase(databaseName));
                }
            });
        }
        return res;
    }
}
