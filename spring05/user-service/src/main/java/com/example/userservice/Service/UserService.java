package com.example.userservice.Service;

import com.example.userservice.DTO.FrontEndMetadataDTO;
import com.example.userservice.DTO.LoginDTO;
import com.example.userservice.DTO.PersonalDataDTO;
import com.example.userservice.Models.User;
import com.example.userservice.Redis.JwtSession;
import com.example.userservice.Repository.JwtSessionRepository;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository cursor;
    private final JwtSessionRepository redisCursor;
    private final JwtUtil jwt;

    @Autowired
    private UserService(UserRepository repository, JwtSessionRepository redis, JwtUtil jwt) {
        this.cursor = repository;
        this.redisCursor = redis;
        this.jwt = jwt;
    }

    public void createUser(User req) {
        cursor.save(req);
    }

    public FrontEndMetadataDTO login(LoginDTO req) throws RuntimeException {
        // AUTH
        User tempUser = cursor.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou senha nao encontrado!"));
        if (!Objects.equals(req.getPassword(), tempUser.getPassword()))
            throw new RuntimeException("Email ou senha nao encontrado!");

        // JWT - SALVA JWT E SESSION INFO EM CACHE
        JwtSession session = new JwtSession(jwt.generateToken(String.valueOf(tempUser.getId())), tempUser.getNome());
        redisCursor.save(session);

        return new FrontEndMetadataDTO(tempUser.getId(), tempUser.getNome(), session.getId());
    }

    public PersonalDataDTO getMyUser(String token) throws RuntimeException
    {
        JwtSession session = redisCursor.findById(token).orElseThrow(() -> new RuntimeException("Sessao invalida!"));
        if (!jwt.validateJwtToken(token))
            throw new RuntimeException("Sessao invalida!");

        UUID id = UUID.fromString(jwt.getUUIDFromToken(token));

        return new PersonalDataDTO(cursor.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao encontrado!")));
    }
}
