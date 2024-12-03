# SP_SpringBoot_AWS_Desafio_03_pedido-estoque-client

In this project, a management system for orders was developed, featuring three (3) microservices built using **Spring** environments and deployed on **AWS**. The system handles the **creation**, **management**, and **manipulation** of data related to **inventory**, **customers**, and **orders**. It includes **GitHub Actions** for build and **testing**, **Swagger** for API documentation, **OpenFeign** for communication between microservices, **dockerizations** of the services, and deployment on **AWS**.

## Estrutura do Projeto

|- ./ms-client <br>
|- ./ms-stock <br>
|- ./ms-order <br>
|- ./Docs <br>
|- docker-compose <br>
|- init.sql <br>
|- Postman_rotes <br>
|- README.md

## Rotas

These are the routes of the project:

- Client
    - POST - Create Client
    - GET - Find All Clients
    - GET - Find Client by ID
    - GET - Find Client by Email
    - PUT - Update Client by ID
    - PUT - Update Client by Email
    - DELETE - Delete Client by Id

- Stock
    - POST - Create Product
    - PUT - Update Product
    - GET - Find All Products
    - GET - Find Product by ID
    - GET - Find Product by Name
    - GET - Remove Quantity
    - GET - Add Quantity
    - DELETE - Delete Product by Id

- Order
    - POST - Create Order
    - PUT - Update Order by ID
    - GET - Find All Orders by Email
    - GET - Find Order by Protocol
    - GET - Find Order by ID
    - GET - Find Product by ID
    - DELETE - Delete Order by ID

- Swagger
    - Stock Controller
        - http://localhost:8080/swagger-ui/index.html

    - Order Controller
        - http://localhost:8081/swagger-ui/index.html
        
    - Client Controller
        - http://localhost:8082/swagger-ui/index.html

## Data Flow

1. Clone the project repository using **git clone**
2. With Docker installed, navigate to the project folder and run the command **docker-compose up -d**
3. After the containers are created, import the **POSTMAN** file from the directory to access the local routes
4. Create a **client** using the client management route
5. Create a **product** using the product management route
6. Create an **order** using the order management route

## Testes

- ms-stock: 94% line coverage
- ms-client: 99% line coverage
- ms-order: 83% line coverage

# Tecnologias Utilizadas

- InteliJ
- VSCode
- PostMan
- Git
- GitHub
- GitHubAction
- Java 21
- SpringFramework
- SpringBoot
- SpringBootTest
- JPA
- Postgres
- Hateoas
- Swagger
- Log4j2
- Docker
- Docker-compose
- DockerHub
- AWS
- EC2

# Video demonstrativo de implantação na AWS

In this video, I demonstrate the **functionality of the routes**, the deployment on **AWS**, and showcase the **logs** recorded using docker logs.

 - https://youtu.be/mjf6pZ2dJGU
