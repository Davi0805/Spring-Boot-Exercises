package com.example.userservice.Redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtSession implements Serializable {

    private String Id; //token
    private String nome;

    // Implementar depois para sistemas complexos de cybersecurity
    //private String ip;
    //private String userAgent;
}
