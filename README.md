# AzureServiceBusClient
Spring Boot Azure Service Bus Client (MVC and REST)

## Dependencies
At least: Java 8 and Maven 3.5

## Build Simple Service Bus Client 
mvn package -DskipTests=true

## Run Simple Service Bus Client 
### Environment variables

### Windows
java -jar target\AzureServiceBus.jar

### Linux (service enabled)
./target/AzureServiceBus.jar start

## Docker build
docker build -t azureservicebus:latest . --build-arg JAR_FILE=./target/AzureServiceBus.jar

## Docker run
docker run --name azureservicebus -d -p 8001:8001 -v /tmp:/tmp azureservicebus:latest
