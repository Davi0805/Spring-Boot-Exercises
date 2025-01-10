package com.example.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.model.Book;
import com.example.spring.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private BookRepository cursor;

    @GetMapping
    public List<Book> getAllBooks(Book req)
    {
        return cursor.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id)
    {
        Optional<Book> book = cursor.findById(id);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        
    }

    @PostMapping
    public Book createBook(Book req)
    {
        return cursor.save(req);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, Book req)
    {
        Optional<Book> book = cursor.findById(id);
        if(book.isPresent())
        {
            Book old = book.get();
            old.setName(req.getName());
            old.setAuthor(req.getAuthor());
            return ResponseEntity.ok(cursor.save(old));
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id)
    {
        if(cursor.existsById(id))
        {
            cursor.deleteById(id);
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.notFound().build();
    }

}
