FROM openjdk:17-jdk-alpine
COPY ./target/Node-0.0.1-SNAPSHOT.jar /usr/app/
COPY ./data /usr/app/data
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","Node-0.0.1-SNAPSHOT.jar"]