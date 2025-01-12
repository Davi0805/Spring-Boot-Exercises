package com.example.spring.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nome nao pode ser vazio")
    private String name;

    @PastOrPresent
    private LocalDate releaseDate; // changed from releasedate to releaseDate

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference // Usar Back na entity filho
    private Author author;

    public Book()
    {

    }

    public Book(String s) {
        this.name = s;
    }
}
