package com.example.userservice.Controller;

import com.example.userservice.DTO.FrontEndMetadataDTO;
import com.example.userservice.DTO.LoginDTO;
import com.example.userservice.Models.User;
import com.example.userservice.Redis.JwtSession;
import com.example.userservice.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    // TODO: LOGICA ROBUSTA DE LOG

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
}
