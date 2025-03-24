package com.example.payment_service.Service;

import com.example.payment_service.Models.Payment;
import com.example.payment_service.Models.PaymentMethod;
import com.example.payment_service.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository cursor;

    @Autowired
    public PaymentService(PaymentRepository service) {
        this.cursor = service;
    }

    @Async
    public void createPaymentRegister(Payment paymentDetails) {
        cursor.save(paymentDetails);
    }

    public void setPaymentSuccess(UUID orderId, boolean success) {
        Payment paymentDetails = cursor.findPaymentByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Falha ao encontrar pagamento!"));

        paymentDetails.setSuccess(success);
        cursor.save(paymentDetails);
    }

    public Payment getPaymentByOrderId(UUID orderId)
    {
        return cursor.findPaymentByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Pagamento nao encontrado!"));
    }
}
