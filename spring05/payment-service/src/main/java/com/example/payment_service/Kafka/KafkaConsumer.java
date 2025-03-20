package com.example.payment_service.Kafka;

import com.example.payment_service.DTO.KafkaOrderMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaConsumer {

    private final ObjectMapper desserializer;

    @Autowired
    public KafkaConsumer(ObjectMapper objM)
    {
        this.desserializer = objM;
    }

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void newOrderListener(String message)
    {
        KafkaOrderMessageDTO orderDetails;
        System.out.println("Mensagem consumida: " + message);
        try {
            orderDetails = desserializer.readValue(message, KafkaOrderMessageDTO.class);
        } catch (JsonProcessingException e) {
            System.out.println("ERRO: Falha ao desserializar Kafka order message");
            throw new RuntimeException(e);
        }

        System.out.println("Mensagem desserializada: " + orderDetails);
    }
}
