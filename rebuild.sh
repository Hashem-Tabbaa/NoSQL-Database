# shellcheck disable=SC2164
cd ./Node
./mvnw clean install
docker rmi -f node
docker build -t node .
cd ../
# shellcheck disable=SC2164
#cd ./BootstrappingNode
#./mvnw clean install
#docker rmi -f bootstrappingnode
#docker build -t bootstrappingnode .