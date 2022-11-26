docker rm -f $(docker ps -aq)
docker network rm db_cluster