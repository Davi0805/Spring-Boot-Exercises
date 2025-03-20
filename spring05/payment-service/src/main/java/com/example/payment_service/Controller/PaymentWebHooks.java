package com.example.payment_service.Controller;

import com.example.payment_service.DTO.KafkaPaymentMessageDTO;
import com.example.payment_service.Service.KafkaProducer;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentWebHooks {

    private final KafkaProducer kafka;

    public PaymentWebHooks(KafkaProducer kafkaProd)
    {
        this.kafka = kafkaProd;
    }

    @GetMapping("/{orderId}")
    public void successPayment(@PathVariable UUID orderId) {
        System.out.println("CALLBACK DE SUCESSO DO PEDIDO: " + orderId);
        kafka.sendMessage("orderPayments", new KafkaPaymentMessageDTO(orderId, true).toString());
    }

    @GetMapping("/failure/{orderId}")
    public void failedPayment(@PathVariable UUID orderId)
    {
        System.out.println("CALLBACK DE FALHA DO PEDIDO: " + orderId);
        kafka.sendMessage("orderPayments", new KafkaPaymentMessageDTO(orderId, false).toString());
    }
}
