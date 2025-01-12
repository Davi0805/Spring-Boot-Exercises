package com.example.spring.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.model.Book;
import com.example.spring.repository.BookRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepository cursor;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = cursor.findAll();
        if (books.isEmpty())
            throw new ResourceNotFoundException("Nenhum Book foi encontrado!");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id)
    {
        Book data = cursor.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book " + id +" nao encontrado!"));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Book> getByName(@PathVariable String name)
    {
        Book data = cursor.findByName(name);
        if (data == null)
            throw new ResourceNotFoundException("Book " + name +" nao encontrado!");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @GetMapping("/release-date-after/{releaseDate}")
    public ResponseEntity<List<Book>> getByReleaseDateAfter(@PathVariable LocalDate releaseDate)
    {
        List<Book> data = cursor.findByReleaseDateAfter(releaseDate);
        if (data.isEmpty())
            throw new ResourceNotFoundException("Nenhum Book foi encontrado!");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @GetMapping("/release-date-year/{year}")
    public ResponseEntity<List<Book>> getByReleaseDateYear(@PathVariable int year) {
        List<Book> data = cursor.findByReleaseDateYear(year);
        if (data.isEmpty())
            throw new ResourceNotFoundException("Nenhum Book foi encontrado!");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    // !Refactor with the proper error handling and if user exists before save it
    @PostMapping
    public Book createBook(@Valid @RequestBody Book req)
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
        data.setReleaseDate(req.getReleaseDate());
        cursor.save(data);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
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
