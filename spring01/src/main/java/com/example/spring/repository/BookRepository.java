package com.example.spring.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByName(String name);
    
    List<Book> findByReleaseDate(LocalDate releaseDate);
    List<Book> findByReleaseDateAfter(LocalDate releaseDate);

    @Query("SELECT b FROM Book b WHERE YEAR(b.releaseDate) = :year")
    List<Book> findByReleaseDateYear(@Param("year") int year);
}

