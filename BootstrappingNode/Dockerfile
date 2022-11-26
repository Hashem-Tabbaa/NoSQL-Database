FROM openjdk:17-jdk-alpine
COPY target/BootstrappingNode-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java","-Dserver.port=8080","-jar","BootstrappingNode-0.0.1-SNAPSHOT.jar"]