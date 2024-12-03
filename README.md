# SP_SpringBoot_AWS_Desafio_03_pedido-estoque-client

Nesse projeto, foi desenvolvido um sistema de gerenciamento de pedidos, onde possui 3(três) microsserviços desenvolvidos em ambientes spring com implantação na AWS. O sistema desempenha a criação, gestão e manipulação de dados de estoque, clientes e pedidos.

## Rotas

Essas são as rotas do projeto

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

## Data Flow

1. Realize o git clone do projeto
2. Com o docker instalado, e na pasta do projeto, realize o comando **docker-compose up -d**.
3. após os containers terem sido criados, importe o arquivo 

1. Criar um cliente através da rota de gerenciamento de cliente.
2. Criar um produto através da rota de gerenciamento de produto.
3. Criar um Pedido através da rota de gerenciamento de pedidos.

# Video demonstrativo de implantação na AWS

https://youtu.be/mjf6pZ2dJGU