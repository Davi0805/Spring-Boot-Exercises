package com.example.payment_service.Controller;

import com.example.payment_service.DTO.KafkaPaymentMessageDTO;
import com.example.payment_service.Models.Payment;
import com.example.payment_service.Service.KafkaProducer;
import com.example.payment_service.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentWebHooks {

    private final KafkaProducer kafka;
    private final PaymentService cursor;

    @Autowired
    public PaymentWebHooks(KafkaProducer kafkaProd, PaymentService cursor)
    {
        this.kafka = kafkaProd;
        this.cursor = cursor;
    }

    @GetMapping("/{orderId}")
    public void successPayment(@PathVariable UUID orderId) {
        System.out.println("CALLBACK DE SUCESSO DO PEDIDO: " + orderId);
        cursor.setPaymentSuccess(orderId, true);
        kafka.sendMessage("orderPayments", new KafkaPaymentMessageDTO(orderId, true).toString());
    }

    @GetMapping("/failure/{orderId}")
    public void failedPayment(@PathVariable UUID orderId)
    {
        System.out.println("CALLBACK DE FALHA DO PEDIDO: " + orderId);
        cursor.setPaymentSuccess(orderId, false);
        kafka.sendMessage("orderPayments", new KafkaPaymentMessageDTO(orderId, false).toString());
    }
}
