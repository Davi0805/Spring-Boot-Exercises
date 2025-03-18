package com.example.userservice.Controller;

import com.example.userservice.DTO.FrontEndMetadataDTO;
import com.example.userservice.DTO.LoginDTO;
import com.example.userservice.DTO.PersonalDataDTO;
import com.example.userservice.Models.User;
import com.example.userservice.Redis.JwtSession;
import com.example.userservice.Service.UserService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.boot.actuate.web.exchanges.Include.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/users")
public class UserController {

    // TODO: LOGICA ROBUSTA DE LOG

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final UserService service;

    @Autowired
    public UserController(UserService user)
    {
        this.service = user;
    }

    @PostMapping("/login")
    public ResponseEntity<FrontEndMetadataDTO> login(@RequestBody LoginDTO req)
    {
        try {
            return ResponseEntity.ok(service.login(req));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User req)
    {
        try {
            service.createUser(req);
            return ResponseEntity.ok("User criado com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Erro: Falha ao criar usuario");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<PersonalDataDTO> getMe(@RequestHeader(AUTHORIZATION_HEADER) String bearerToken) {
        try {
            String token = extractToken(bearerToken);
            return ResponseEntity.ok(service.getMyUser(token));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private String extractToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        throw new RuntimeException("Invalid Authorization header");
    }
}
