package com.example.spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerar id automaticanente
    private long id;

    private String name;
    private String author;
}
