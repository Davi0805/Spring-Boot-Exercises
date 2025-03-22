package com.example.orderservice.Kafka;

import com.example.orderservice.DTO.KafkaPaymentMessageDTO;
import com.example.orderservice.Models.Order;
import com.example.orderservice.Models.OrderStatus;
import com.example.orderservice.Service.OrderService;
import com.example.orderservice.Service.grpcOrderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final ObjectMapper desserializer;
    private final OrderService cursor;
    private final grpcOrderClient grpcClient;

    @Autowired
    public KafkaConsumer(ObjectMapper objM, OrderService orderService,
                         grpcOrderClient grpcClient) {
        this.desserializer = objM;
        this.cursor = orderService;
        this.grpcClient = grpcClient;
    }

    @KafkaListener(topics = "orderPayments", groupId = "order-group")
    public void paymentStatus(String message)
    {
        System.out.println("Mensagem escutada no orderPayments: " + message);
        try {
            KafkaPaymentMessageDTO dto = desserializer.readValue(message, KafkaPaymentMessageDTO.class);
            Order temp = cursor.getOrderByIdWithItems(dto.getOrderId());
            if (dto.isSuccess())
                temp.setStatus(OrderStatus.PROCESSING);
            else {
                temp.setStatus(OrderStatus.CANCELLED);
                grpcClient.cancelOrder(String.valueOf(temp.getId()), temp.getItems());
            }
            cursor.createOrUpdateOrder(temp);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
