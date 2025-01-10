package com.example.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.spring.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByName(String name);
    
    @Query("SELECT b FROM Book b WHERE b.author.name == $1")
    List<Book> findByAuthorName(String authorname);
}
