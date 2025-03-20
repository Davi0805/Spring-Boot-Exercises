package com.example.payment_service.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentWebHooks {

    @GetMapping("/{orderId}")
    public void successPayment(@PathVariable UUID orderId) {
        System.out.println("CALLBACK DE SUCESSO DO PEDIDO: " + orderId);
    }

    @GetMapping("/failure/{orderId}")
    public void failedPayment(@PathVariable UUID orderId)
    {
        System.out.println("CALLBACK DE FALHA DO PEDIDO: " + orderId);
    }
}
