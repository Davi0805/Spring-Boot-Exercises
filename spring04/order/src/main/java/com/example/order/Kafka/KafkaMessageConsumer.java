package com.example.order.Kafka;

import com.example.order.Models.Order;
import com.example.order.Repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.example.order.Kafka.MessageProducer;

import java.util.UUID;

import static com.example.order.Models.OrderStatus.NOTIFIED;

@Component
public class KafkaMessageConsumer {

    private final ObjectMapper desserializer;
    private final MessageProducer messageProducer;
    private final OrderRepository cursor;

    @Autowired
    public KafkaMessageConsumer(ObjectMapper desserializer, MessageProducer producer, OrderRepository orderRepo)
    {
        this.desserializer = desserializer;
        this.messageProducer = producer;
        this.cursor = orderRepo;
    }

    @KafkaListener(topics = "emailNotified", groupId = "notification-group")
    public void listen(String message)
    {
        System.out.println("Pedido enviado para email: " + message);

        //LOGICA DE EMAIL OU NOTIFICACAO ACONTECERIA AQUUI
        Order order = cursor.findById(UUID.fromString(message)).orElseThrow(() -> new RuntimeException());
        order.setStatus(NOTIFIED);
        cursor.save(order);
    }
}
