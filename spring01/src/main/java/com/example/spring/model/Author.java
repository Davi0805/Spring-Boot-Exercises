package com.example.spring.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nome nao pode ser vazio!")
    private String name;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Lembrar de usar Managed na Entity Pai
    private List<Book> books;

    public Author()
    {

    }

    public Author(String s) {
        this.name = s;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        for (Book book : books) {
            book.setAuthor(this);
        }
    }

    @Override
    public String toString()
    {
        return "{ name: \"" + name + "\", books: \"" + books + "\"}";
    }
}
