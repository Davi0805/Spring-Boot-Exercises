package com.example.spring;

import com.example.spring.model.Author;
import com.example.spring.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthorTests {

    @Test
    public void testAuthor()
    {
        Author test1 = new Author();
        test1.setId(1L);
        test1.setName("Davi");
        List<Book> books = new ArrayList<Book>();
        books.add(new Book("Livro 1"));
        books.add(new Book("Livro 2"));
        test1.setBooks(books);

        assertEquals(1L, test1.getId());
        assertEquals("Davi", test1.getName());
        assertEquals(books, test1.getBooks());
    }
}
