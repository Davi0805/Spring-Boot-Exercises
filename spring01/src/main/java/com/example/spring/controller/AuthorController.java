package com.example.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.model.Author;
import com.example.spring.repository.AuthorRepository;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorRepository cursor;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors()
    {
        List<Author> data = cursor.findAll();
        if (data.isEmpty())
            throw new ResourceNotFoundException("Author nao encontrado!");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id)
    {
        Author data = cursor.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Author nao encontrado!"));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @PostMapping
    public Author createAuthor(Author author)
    {
        return cursor.save(author);
    } // Modificar para created

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, Author author) {
        Author data = cursor.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Author nao encontrado!"));
        data.setName(author.getName());
        data.setBooks(author.getBooks());
        cursor.save(data);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        if (cursor.existsById(id))
        {
            cursor.deleteById(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }
        throw new ResourceNotFoundException("Author nao encontrado!");
    }
}
