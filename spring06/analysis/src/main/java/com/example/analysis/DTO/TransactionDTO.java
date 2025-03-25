package com.example.analysis.DTO;

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
    private Date timestamp;
    private String location;
}
