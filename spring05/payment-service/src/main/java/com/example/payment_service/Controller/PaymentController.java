package com.example.payment_service.Controller;

import com.example.payment_service.Models.Payment;
import com.example.payment_service.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/invoice")
public class PaymentController {

    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService service)
    {
        this.service = service;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID orderId)
    {
        try {
            //TODO: ADD AUTH WHEN START TO RECEIVE USERID VIA gRPC
            Payment payment = service.getPaymentByOrderId(orderId);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
