package com.database.atypon.BootstrappingNode;

import com.database.atypon.BootstrappingNode.model.Node;
import com.database.atypon.BootstrappingNode.model.User;
import com.database.atypon.BootstrappingNode.response.Response;
import com.sun.net.httpserver.Headers;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Vector;

@SpringBootApplication
public class BootstrappingNodeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BootstrappingNodeApplication.class, args);
	}

	@Override
	public void run(String... args) {

		RestTemplate restTemplate = new RestTemplate();
		Node node0 = new Node("Node0", "8080");
		Node node1 = new Node("Node1", "8080");
		Node node2 = new Node("Node2", "8080");
//		Node node3 = new Node("Node3", "8080", 3);
//		Node node4 = new Node("Node4", "8080", 4);
		List<Node> nodes = List.of(node0, node1, node2);

		for(int i = 0 ; i<3 ; i++) {
			List<Node> nodesToAdd = new Vector<>();
			for(int j = 0 ; j<3 ; j++)
				if(i != j)
					nodesToAdd.add(nodes.get(j));

			String url = nodes.get(i).getURL() + "/network/add/nodes";
			String url2 = nodes.get(i).getURL() + "/network/assign/self";

			try {
				String response = restTemplate.postForObject(url, nodesToAdd, String.class);
				String response2 = restTemplate.postForObject(url2, nodes.get(i), String.class);
			} catch (Exception e) {
			}
		}
	}
}
