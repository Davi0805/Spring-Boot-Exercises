package com.example.payment_service.Models;

import com.example.payment_service.Service.PaymentService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private boolean success;

    public Payment(UUID orderId, double totalPrice, PaymentMethod method)
    {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.paymentMethod = method;
    }
}
