package com.example.orderservice.Kafka;

import com.example.orderservice.DTO.KafkaPaymentMessageDTO;
import com.example.orderservice.Models.Order;
import com.example.orderservice.Models.OrderStatus;
import com.example.orderservice.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private ObjectMapper desserializer;
    private OrderService cursor;

    @Autowired
    public KafkaConsumer(ObjectMapper objM, OrderService orderService)
    {
        this.desserializer = objM;
        this.cursor = orderService;
    }

    @KafkaListener(topics = "orderPayments", groupId = "order-group")
    public void paymentStatus(String message)
    {
        System.out.println("Mensagem escutada no orderPayments: " + message);
        try {
            KafkaPaymentMessageDTO dto = desserializer.readValue(message, KafkaPaymentMessageDTO.class);
            Order temp = cursor.getOrderById(dto.getOrderId());
            if (dto.isSuccess())
                temp.setStatus(OrderStatus.PROCESSING);
            else
                temp.setStatus(OrderStatus.CANCELLED);
            cursor.createOrUpdateOrder(temp);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
