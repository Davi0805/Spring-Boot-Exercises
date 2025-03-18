package com.example.notification.Kafka.Consumer;

import com.example.notification.Kafka.Producer.MessageProducer;
import com.example.notification.Models.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {

    private final ObjectMapper desserializer;
    private final MessageProducer messageProducer;

    @Autowired
    public KafkaMessageConsumer(ObjectMapper desserializer, MessageProducer producer)
    {
        this.desserializer = desserializer;
        this.messageProducer = producer;
    }

    @KafkaListener(topics = "emailNotification", groupId = "notification-group")
    public void listen(String message)
    {
        //System.out.println("Mensagem recebida no Consumer: " + message);

        //LOGICA DE EMAIL OU NOTIFICACAO ACONTECERIA AQUUI
        // POREM NAO QUERO GASTAR MEU SENDGRID COM O EXERCICIO

        // CHAMA CONSUMER PARA MODIFICAR STATUS DO ORDER PARA NOTIFICADO
        // CONSUMER MANDA MENSAGEM DE VOLTA PARA MICROSERVICO ORDER E MUDA STATUS PARA NOTIFICADO
        try {
            Order order = desserializer.readValue(message, Order.class);
            //System.out.println("Order deserializado: " + order);
            messageProducer.sendMessage("emailNotified", String.valueOf(order.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
