package com.example.orderservice.Controller;

import com.example.orderservice.Redis.JwtSession;
import com.example.orderservice.Repository.JwtSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import static org.springframework.web.servlet.function.ServerResponse.badRequest;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtSessionRepository redisCursor;

    @Autowired
    public OrderController(JwtSessionRepository redisCursor) {
        this.redisCursor = redisCursor;
    }

    @GetMapping
    public ResponseEntity<?> getOrders(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        token = extractToken(token);
        System.out.println(token);
        JwtSession session;

        try {
            session = redisCursor.findById(token).orElseThrow(() -> new RuntimeException("Token não encontrado"));
        } catch (Exception e) {
            System.out.println("Erro ao buscar token");
            return ResponseEntity.badRequest().body("Erro ao buscar token");
        }

        return ResponseEntity.ok(session);
    }

    private String extractToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        throw new RuntimeException("Header de autorização inválido");
    }

}
