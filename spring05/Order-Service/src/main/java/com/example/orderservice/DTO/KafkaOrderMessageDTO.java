package com.example.orderservice.DTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KafkaOrderMessageDTO {
    private UUID orderId;
    private UUID userId;
    private String email;
    private double price; // tlvz para ser usado no payment service

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar DTO");
        }
    }
}
