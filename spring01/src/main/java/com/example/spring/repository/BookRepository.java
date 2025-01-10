package com.example.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
}
