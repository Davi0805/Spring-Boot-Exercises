package com.example.spring.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerar id automaticanente
    private long Id;

    private String name;
    private String author;
}
