package com.example.order_service.Controller;

import com.example.order_service.DTO.TransactionDTO;
import com.example.order_service.Services.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final KafkaProducer kafka;

    public TransactionsController(KafkaProducer kafka) {
        this.kafka = kafka;
    }

    // TODO: Dynamic userId based on JwtToken

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO request)
    {
        try {
            kafka.send("transactions", request.toString());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
