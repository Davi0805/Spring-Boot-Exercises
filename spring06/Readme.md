# 🏗 **Arquitetura do Sistema de Análise de Transações**  

📌 **Descrição**  
Este projeto implementa um sistema de análise de transações financeiras usando **event-driven architecture**. As transações são recebidas por meio de eventos e analisadas para detectar padrões incomuns, como:  
✅ Transações acima de um limite definido.  
✅ Transações duplicadas em um curto período de tempo.  
✅ Picos anormais de transações para um mesmo usuário.  

🏛 **Microserviços**  

1️⃣ **Transaction Service (Serviço de Transações)**  
   - Exposto via REST.  
   - Recebe transações dos usuários.  
   - Publica eventos no Kafka para análise.  

2️⃣ **Analysis Service (Serviço de Análise)**  
   - Escuta eventos do Kafka.  
   - Aplica regras básicas de análise (limites, duplicações, picos).  
   - Publica alertas se detectar anomalias.  

3️⃣ **Alert Service (Serviço de Alertas)**  
   - Consome eventos de anomalias do Kafka.  
   - Envia notificações (e-mail/SMS) ao usuário ou equipe de segurança.  

🔗 **Fluxo de Comunicação**  
- O **Transaction Service** recebe transações e publica no **Kafka**.  
- O **Analysis Service** escuta as transações e verifica se há anomalias.  
- Se detectar algo suspeito, publica um evento de **alerta**.  
- O **Alert Service** consome o alerta e dispara uma notificação.  

🗃 **Modelo de Dados**  

💳 **Transaction (Transação)**  
```json
{
  "id": "UUID",
  "user_id": "UUID",
  "amount": 1500.00,
  "timestamp": "2024-03-24T12:00:00Z",
  "location": "São Paulo, BR"
}
```
🚨 **Alert (Alerta de Anomalia)**  
```json
{
  "transaction_id": "UUID",
  "user_id": "UUID",
  "reason": "Transaction amount exceeded limit",
  "severity": "HIGH"
}
```

🌐 **Endpoints**  

📌 **Transaction Service (REST)**  
- `POST /api/transactions` → Registra uma nova transação e envia ao Kafka.  
- `GET /api/transactions/{id}` → Retorna detalhes de uma transação.  

📌 **Analysis Service (Kafka Consumer)**  
- `transaction-created` → Consome eventos e analisa transações.  
- Se detectar algo suspeito, publica um evento `transaction-alert`.  

📌 **Alert Service (Kafka Consumer)**  
- `transaction-alert` → Envia notificação ao usuário.  

🚀 **Tecnologias Utilizadas**  
- **Linguagem:** Java (Spring Boot) ou C++ (Boost, Hiredis para Redis, etc.)  
- **Mensageria:** Kafka  
- **Banco de Dados:** PostgreSQL (armazenamento de transações), Redis (cache para transações recentes)  
- **Infraestrutura:** Docker + Docker Compose  

---  

Que acha dessa abordagem? Podemos começar com algo bem básico e ir adicionando regras mais complexas depois! 🔥
