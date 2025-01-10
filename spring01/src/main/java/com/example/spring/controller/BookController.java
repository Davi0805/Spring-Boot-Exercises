package com.example.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.model.Book;
import com.example.spring.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepository cursor;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = cursor.findAll();
        if (books.isEmpty())
            throw new ResourceNotFoundException("Book nao encontrado!");
        return ResponseEntity.ok(books);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id)
    {
        Book data = cursor.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book nao encontrado!"));

        return ResponseEntity.ok(data);
    }

    @PostMapping
    public Book createBook(Book req)
    {
        return cursor.save(req);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id , Book req)
    {
        Book data = cursor.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book nao encontrado!"));
        
        data.setName(req.getName());
        data.setAuthor(req.getAuthor());
        data.setRelease_date(req.getRelease_date());
        cursor.save(data);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        if (cursor.existsById(id)) {
            cursor.deleteById(id);
            return ResponseEntity.ok().build();
        }
        throw new ResourceNotFoundException("Book nao encontrado");
    }
}
