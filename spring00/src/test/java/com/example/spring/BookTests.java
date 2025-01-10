package com.example.spring;


import static org.junit.jupiter.api.Assertions.assertEquals;

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
        test.setAuthor("Autor do livro");

        assertEquals(1L, test.getId());
        assertEquals("NomeDoLivro", test.getName());
        assertEquals("Autor do livro", test.getAuthor());
    }
}
