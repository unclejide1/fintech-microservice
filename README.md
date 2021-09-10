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

## Start all the services

- make ddev


## Swagger url's

- Account-service: http://localhost:8765/accounts/swagger-ui.html

- Transaction-Service: http://localhost:8765/transaction/swagger-ui.html


## Postman Collection for Demo
- https://www.getpostman.com/collections/7d6ba2a08d85560bc5c1

