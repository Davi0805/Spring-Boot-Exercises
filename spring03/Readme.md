# gRPC E-Commerce Microservices

## Descrição
Este exercício envolve a implementação de comunicação entre microserviços usando gRPC em um ambiente de e-commerce. O objetivo é criar dois microserviços que interajam via gRPC: um para gerenciamento de pedidos e outro para gerenciamento de produtos.

## Funcionalidades
- Criar e consultar pedidos
- Consultar estoque de produtos
- Atualizar estoque quando um pedido é criado

## Modelos de Domínio
- **Pedido (Order):** Representa uma ordem de compra com ID, lista de produtos e status.
- **Produto (Product):** Contém ID, nome e quantidade em estoque.

## Microserviços
1. **Order Service** (Serviço de Pedidos)
    - Exposto via REST
    - Se comunica com o Product Service via gRPC
    - Responsável por criar pedidos e verificar estoque antes da confirmação

2. **Product Service** (Serviço de Produtos)
    - Exposto via gRPC
    - Gerencia o estoque de produtos
    - Informa ao Order Service se os produtos estão disponíveis

## Endpoints
### Order Service (REST)
- `POST /api/orders` - Cria um novo pedido e consulta o estoque via gRPC
- `GET /api/orders/{id}` - Retorna os detalhes de um pedido

### Product Service (gRPC)
- `CheckStock(ProductRequest) -> StockResponse` - Verifica se um produto tem estoque suficiente
- `UpdateStock(OrderRequest) -> OrderResponse` - Deduz estoque quando um pedido é confirmado

## Passos para Implementação
1. Criar os proto files definindo os contratos do Product Service.
2. Implementar o servidor gRPC para o Product Service.
3. Criar o cliente gRPC no Order Service para se comunicar com o Product Service.
4. Desenvolver os endpoints REST para receber pedidos no Order Service.
5. Configurar o projeto para rodar os microserviços separadamente.
6. Testar a comunicação gRPC com o Order Service chamando o Product Service.

## Tecnologias Utilizadas
- Spring Boot
- gRPC com Spring Boot Starter
- Protocol Buffers (.proto)
- Docker (opcional)
- PostgreSQL ou H2 para persistência (opcional)

## Como Rodar
1. Clone este repositório
2. Rode os dois microserviços (Order Service e Product Service) separadamente
3. Utilize um cliente REST (como Postman) para criar pedidos
4. Verifique os logs para confirmar a comunicação gRPC

Boa prática! 🚀

