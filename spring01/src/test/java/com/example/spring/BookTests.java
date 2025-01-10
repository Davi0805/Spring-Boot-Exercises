package com.example.spring;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.spring.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spring.model.Book;

@SpringBootTest
public class BookTests {
    
    @Test
    public void testBook() {
        Book test = new Book();
        test.setId(1L);
        test.setName("NomeDoLivro");
        test.setAuthor(new Author("AuthorName"));

        assertEquals(1L, test.getId());
        assertEquals("NomeDoLivro", test.getName());
        assertEquals(new Author("AuthorName").toString(), test.getAuthor().toString());
    }
}
