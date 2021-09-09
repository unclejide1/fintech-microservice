# microservices-with-eureka-and-kafka
A simple microservice architectured system built with springboot, eureka and kafka.

#### Prerequisite
- Java 11
- Docker

To start the application run the following commands
#### Docker-Compose
- cd "fintech-microservice"
- docker-compose up


### Config server
- cd config-server
- docker build -t fintech/configserver-builder:latest --cache-from fintech/configserver-builder:latest . -f Dockerfile-builder
-docker build -t fintech/config-server .
- docker run -dp 9600:9600 fintech/config-server


### Discovery server
- cd ..
- cd discovery-server
- docker build -t fintech/discoveryserver-builder:latest --cache-from fintech/discoveryserver-builder:latest . -f Dockerfile-builder
- docker build -t fintech/discovery-server .
- docker run -dp 8761:8761 fintech/discovery-server


### Gateway server
- cd ..
- cd gateway-service
- docker build -t fintech/gatewayserver-builder:latest --cache-from fintech/gatewayserver-builder:latest . -f Dockerfile-builder
- docker build -t fintech/gateway-server .
- docker run -dp 8765:8765 --network host fintech/gateway-server

### Account service
- cd ..
- cd account-service
- docker build -t fintech/accountserver-builder:latest --cache-from fintech/accountserver-builder:latest . -f Dockerfile-builder
- docker build -t fintech/account-server .
- docker run -dp 9800:9800 --network host fintech/account-server
