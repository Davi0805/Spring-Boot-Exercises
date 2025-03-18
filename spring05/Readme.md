# 🏗 **Arquitetura de Microservices para E-Commerce**  

## 📌 **Descrição**  

Este projeto implementa um sistema de e-commerce distribuído utilizando **microserviços**, onde diferentes partes do sistema interagem entre si via **REST, gRPC, Kafka e Redis**. A arquitetura inclui **gerenciamento de pedidos, produtos, pagamentos e notificações**, seguindo boas práticas de comunicação assíncrona e escalabilidade.

---

## 🏛 **Microserviços**  

### 1️⃣ **Order Service (Serviço de Pedidos)**
- Exposto via **REST**.
- Se comunica com **Product Service** via **gRPC** para validar estoque.
- Publica eventos no **Kafka** quando um pedido é criado.
- Consome eventos de pagamento para atualizar o status do pedido.

### 2️⃣ **Product Service (Serviço de Produtos)**
- Exposto via **gRPC**.
- Mantém o estoque dos produtos.
- Fornece informações de disponibilidade de estoque para o **Order Service**.
- Deduz o estoque quando um pedido é confirmado.

### 3️⃣ **Payment Service (Serviço de Pagamentos)**
- Exposto via **REST**.
- Processa pagamentos e publica eventos no **Kafka**.
- Atualiza o status do pagamento e envia evento de confirmação para **Order Service**.

### 4️⃣ **Notification Service (Serviço de Notificações)**
- Escuta eventos de **Kafka** para enviar notificações por e-mail/SMS.
- Pode usar **SendGrid** para e-mail e **Twilio** para SMS.

### 5️⃣ **User Service (Serviço de Usuários)**
- Exposto via **REST**.
- Mantém cadastro e autenticação dos usuários.
- Usa **JWT** para autenticação.
- Armazena tokens no **Redis** para controle de sessão.

---

## 🔗 **Comunicação entre Microservices**  

- **Order Service ⇄ Product Service** → gRPC (Consulta e atualização de estoque).
- **Order Service → Kafka → Payment Service** → Mensagem assíncrona para processar pagamento.
- **Payment Service → Kafka → Order Service** → Mensagem assíncrona confirmando pagamento.
- **Kafka → Notification Service** → Evento de "Pedido Confirmado" para envio de e-mail/SMS.
- **User Service** → REST para cadastro e autenticação.

---

## 🗃 **Modelos de Domínio**  

### **Order (Pedido)**
```json
{
  "id": "UUID",
  "user_id": "UUID",
  "items": [
    { "product_id": "UUID", "quantity": 2 }
  ],
  "total_price": 120.00,
  "status": "PENDING"
}
```

### **Product (Produto)**
```json
{
  "id": "UUID",
  "name": "Camiseta",
   "description": "camisa da lacoste",
   "urlImage": "<urlParaBucket>",
  "stock": 50,
  "price": 60.00,
   "last_pieces": true
}
```

### **Payment (Pagamento)**
```json
{
  "id": "UUID",
  "order_id": "UUID",
  "status": "CONFIRMED",
  "transaction_id": "STRIPE_ID"
}
```

---

## 🌐 **Endpoints**

### **Order Service (REST)**
- `POST /api/orders` → Criar um novo pedido e validar estoque via gRPC.
- `GET /api/orders/{id}` → Retornar detalhes do pedido.

### **Product Service (gRPC)**
- `CheckStock(ProductRequest) -> StockResponse` → Verificar se o produto tem estoque.
- `UpdateStock(OrderRequest) -> OrderResponse` → Deduz estoque após a confirmação do pedido.

### **Payment Service (REST)**
- `POST /api/payments` → Processar pagamento e emitir evento Kafka.

### **Notification Service (Kafka Consumer)**
- `Pedido Confirmado → Enviar notificação`.

### **User Service (REST)**
- `POST /api/users/register` → Cadastrar usuário.
- `POST /api/users/login` → Autenticar e gerar JWT.

---

## 🚀 **Tecnologias Utilizadas**

### **Linguagens e Frameworks**
- **Spring Boot** → Backend dos microserviços.
- **gRPC** → Comunicação entre serviços de Pedido e Produto.
- **Kafka** → Mensageria assíncrona entre Pedido, Pagamento e Notificação.
- **Redis** → Cache para autenticação de usuários e controle de sessão.

### **Banco de Dados**
- **PostgreSQL** → Persistência dos pedidos e usuários.
- **Redis** → Armazena sessões JWT.
- **Kafka Topics** → `order-created`, `payment-confirmed`, `order-updated`.

### **Infraestrutura**
- **Docker & Docker Compose** → Para subir toda a infraestrutura facilmente.
- **Kubernetes (opcional)** → Para orquestração dos serviços.
- **Prometheus & Grafana** → Monitoramento de logs e métricas.

---

## ⚙ **Como Rodar o Projeto**

1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-repo/ecommerce-microservices.git
   cd ecommerce-microservices
   ```

2. Suba os serviços com **Docker Compose**:
   ```sh
   docker-compose up -d
   ```

3. Teste os serviços:
    - Criar um pedido via **REST**:
      ```sh
      curl -X POST http://localhost:8080/api/orders -H "Content-Type: application/json" -d '{
        "user_id": "123",
        "items": [{ "product_id": "abc", "quantity": 2 }]
      }'
      ```

4. Verifique os logs dos microserviços para conferir a comunicação.

---

## 🎯 **Objetivos do Exercício**

✅ Implementar comunicação **REST, gRPC e Kafka** entre microserviços.  
✅ Entender mensageria assíncrona para **event-driven architecture**.  
✅ Usar **Redis** para autenticação e cache.  
✅ Criar um sistema escalável e distribuído.

🚀 **Bora codar!**
