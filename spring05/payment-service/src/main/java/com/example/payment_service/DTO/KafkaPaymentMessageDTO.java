package com.example.payment_service.DTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KafkaPaymentMessageDTO {
    private UUID orderId;
    private boolean success;

    @Override
    public String toString()
    {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro: ao desserializar DTO | " + e.getMessage());
        }
    }
}
