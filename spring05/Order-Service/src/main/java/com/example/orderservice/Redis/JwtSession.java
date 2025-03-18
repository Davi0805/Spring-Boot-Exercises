package com.example.orderservice.Redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.index.qual.SearchIndexBottom;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("Sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtSession implements Serializable {
    @Id
    private String id;
    private String email;
    private String nome;

}
