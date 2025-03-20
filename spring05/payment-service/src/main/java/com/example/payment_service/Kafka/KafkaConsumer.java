package com.example.payment_service.Kafka;

import com.example.payment_service.DTO.KafkaOrderMessageDTO;
import com.example.payment_service.Service.StripePaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaConsumer {

    private final ObjectMapper desserializer;
    private final StripePaymentService payService;

    @Autowired
    public KafkaConsumer(ObjectMapper objM, StripePaymentService payment)
    {
        this.desserializer = objM;
        this.payService = payment;
    }

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void newOrderListener(String message)
    {
        KafkaOrderMessageDTO orderDetails;
        System.out.println("Mensagem consumida: " + message);
        try {
            orderDetails = desserializer.readValue(message, KafkaOrderMessageDTO.class);
            String paymentUrl = payService.createPaymentLink(orderDetails);
            System.out.println("[DEBUG]: URL PARA PAGAMENTO - " + paymentUrl);
        } catch (Exception e) {
            System.out.println("ERRO: Falha ao processar order recebido do kafka | " + e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println("Mensagem desserializada: " + orderDetails);
    }
}
