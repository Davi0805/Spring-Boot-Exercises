package com.example.userservice.DTO;

import com.example.userservice.Models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PersonalDataDTO {

    public PersonalDataDTO(User user) {
        this.nome = user.getNome();
        this.sobrenome = user.getSobrenome();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

    private String nome;
    private String sobrenome;
    private String email;
    private String username;
}
