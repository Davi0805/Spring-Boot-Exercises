package com.example.analysis.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AlertDTO {

    public enum Severity {
        HIGH,
        MEDIUM,
        LOW
    }

    private UUID transaction_id;
    private UUID user_id;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Override
    public String toString()
    {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
