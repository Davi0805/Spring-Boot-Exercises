package com.example.userservice.Service;

import com.example.userservice.DTO.FrontEndMetadataDTO;
import com.example.userservice.DTO.LoginDTO;
import com.example.userservice.Models.User;
import com.example.userservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository cursor;

    @Autowired
    private UserService(UserRepository repository)
    {
        this.cursor = repository;
    }

    public void createUser(User req)
    {
        cursor.save(req);
    }

    public FrontEndMetadataDTO login(LoginDTO req)
    {
        User tempUser = cursor.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou senha nao encontrado!"));

        if (!Objects.equals(req.getPassword(), tempUser.getPassword()))
            throw new RuntimeException("Email ou senha nao encontrado!");

        return new FrontEndMetadataDTO(tempUser.getNome(), tempUser.getSobrenome());
    }
}
