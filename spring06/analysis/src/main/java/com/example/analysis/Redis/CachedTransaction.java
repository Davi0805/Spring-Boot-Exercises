package com.example.analysis.Redis;

import com.example.analysis.DTO.TransactionDTO;
import jakarta.persistence.Cache;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash(value = "Transaction", timeToLive = 180L)
public class CachedTransaction {
    @Id
    private UUID id;

    @Indexed
    private UUID userId;

    @Indexed
    private double amount;

    // irrelevant cause is only going to be stored in redis for 3 min anyway
    // but maybe a prod env would have to be sure even in a latency situation
    // and maybe i`ll put it back later lol
    //private LocalDateTime timestamp;

    private String location;

    public CachedTransaction(TransactionDTO dto)
    {
        this.id = dto.getId();
        this.amount = dto.getAmount();
        this.userId = dto.getUser_id();
        //this.timestamp = dto.getTimestamp();
        this.location = dto.getLocation();
    }
}
