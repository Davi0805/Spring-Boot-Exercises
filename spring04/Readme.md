# Exercício: E-Commerce com Kafka e Microservices

## Descrição

Este exercício envolve a criação de um sistema de e-commerce com dois microserviços principais, onde um dos microserviços de **Pedidos** vai gerar eventos de pedidos realizados, e o microserviço de **Notificações** vai escutar esses eventos e enviar notificações (por exemplo, e-mail ou SMS) para o usuário.

O objetivo aqui é criar uma arquitetura baseada em eventos com Kafka como sistema de mensageria, permitindo que os microserviços se comuniquem de maneira assíncrona.

## Funcionalidades

### Pedido Service:
- Criar pedidos.
- Publicar um evento de "Pedido Criado" no Kafka.

### Notificação Service:
- Escutar os eventos de "Pedido Criado".
- Enviar uma notificação (exemplo: e-mail ou SMS) quando um pedido for criado.

## Modelos de Domínio

### Pedido (Order):
- ID do pedido
- Lista de produtos
- Status do pedido
- Data de criação

### Notificação (Notification):
- Tipo de notificação (E-mail, SMS)
- Detalhes do pedido
- Destinatário

## Endpoints

### Pedido Service (REST):
- `POST /api/orders` - Cria um novo pedido e publica um evento de "Pedido Criado".

### Notificação Service (Kafka):
- O microserviço de notificação escutará os eventos de Kafka para processar notificações.

## Passos para Implementação

1. **Configuração do Kafka**:
   - Instalar e configurar o Kafka.
   - Criar tópicos necessários para a comunicação (por exemplo, `order-created`).

2. **Definir os contratos de mensagens com Kafka**:
   - Usar **Protocol Buffers** ou **JSON** para definir o formato das mensagens que serão enviadas/recebidas.

3. **Implementar o Pedido Service**:
   - Implementar um endpoint REST para criar pedidos.
   - Após a criação do pedido, gerar um evento Kafka com as informações do pedido.

4. **Implementar o Notificação Service**:
   - Criar um consumidor Kafka que escute os eventos de "Pedido Criado".
   - Após a recepção do evento, enviar uma notificação para o usuário.

5. **Implementar as Notificações**:
   - Utilizar uma API de envio de e-mail (como **SendGrid** ou **JavaMail**) ou SMS para enviar notificações.

6. **Testar**:
   - Testar a criação de pedidos e a escuta dos eventos pelo Notificação Service.
   - Verificar se as notificações estão sendo enviadas corretamente.

## Tecnologias Utilizadas

- **Spring Boot** para os microserviços.
- **Spring Kafka** para integrar o Kafka com o Spring Boot.
- **Kafka** como sistema de mensageria.
- **Protocol Buffers** ou **JSON** para definir os contratos de mensagens.
- **PostgreSQL** ou **H2** para persistência de pedidos.
- **Docker** para rodar os microserviços e o Kafka.
- **SendGrid/JavaMail** ou uma API de SMS para enviar notificações.

## Como Rodar

1. Clone o repositório.
2. Configure o Kafka e crie os tópicos necessários.
3. Rode os dois microserviços (Pedido Service e Notificação Service) separadamente.
4. Utilize um cliente REST (como o Postman) para criar pedidos.
5. Verifique os logs do Notificação Service para confirmar o recebimento dos eventos e o envio de notificações.

## Objetivo

- **Aprender a trabalhar com sistemas baseados em eventos** usando Kafka.
- **Entender a comunicação assíncrona** entre microserviços.
- **Desenvolver habilidades em mensageria** com Kafka, uma tecnologia amplamente usada em arquiteturas de microserviços e sistemas distribuídos.
