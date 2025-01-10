package com.example.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
