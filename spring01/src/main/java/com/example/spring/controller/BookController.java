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
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = cursor.findAll();
        if (books.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(books);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id)
    {
        Book data = cursor.findById(id).orElse(null);
        if (data == null)
            return ResponseEntity.notFound().build();
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
        Optional<Book> data = cursor.findById(id);
        if (data.isPresent())
        {
            Book book = data.get();
            book.setName(req.getName());
            book.setAuthor(req.getAuthor());
            cursor.save(book);
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        if (cursor.existsById(id)) {
            cursor.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
