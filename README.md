# microservices-with-eureka-and-kafka
A simple microservice architectured system built with springboot, eureka.

#### Prerequisite
- Java 11
- Docker

To start the application run the following commands
- cd fintech-microservice


## Build the services

### Config server
- make build-config

### Discovery server
- make build-disc

### Gateway server
- make build-gateway

### Account service
- make build-acc

### Transaction service
- make build-trans

### Notification service
- make build-notify

#### Start all the services

- make ddev

