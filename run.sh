#!/bin/bash
echo "Enter the number nodes you want to create in the cluster"
read -r num

#Creating the cluster's network
docker network create -d bridge db_cluster

# Create num number of nodes of image type (Node) and name (Node1, Node2, Node3, etc)
for ((i=0; i<num; i++)) do
  docker run --name Node"$i" -p 808"$i":8080 --network=db_cluster -d node
done
docker run --name BootstrappingNode -p 8079:8080 --network=db_cluster -d bootstrappingnode

