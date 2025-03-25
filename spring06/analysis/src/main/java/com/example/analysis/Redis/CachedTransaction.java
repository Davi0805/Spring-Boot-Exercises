package com.example.analysis.Redis;

import com.example.analysis.DTO.TransactionDTO;
import jakarta.persistence.Cache;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash("Transaction")
public class CachedTransaction {
    @Id
    private UUID id;
    private UUID user_id;
    private double amount;
    private LocalDateTime timestamp;
    private String location;

    public CachedTransaction(TransactionDTO dto)
    {
        this.id = dto.getId();
        this.amount = dto.getAmount();
        this.user_id = dto.getUser_id();
        this.timestamp = dto.getTimestamp();
        this.location = dto.getLocation();
    }
}
