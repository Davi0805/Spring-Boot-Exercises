package com.example.analysis.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDTO {
    private UUID id;
    private UUID user_id;
    private double amount;
    private LocalDateTime timestamp;
    private String location;
}
