package com.example.spring;

import com.example.spring.model.Author;
import com.example.spring.model.Book;
import com.example.spring.repository.AuthorRepository;
import com.example.spring.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorIntegrationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext WebContext;

    @Autowired
    AuthorRepository authorCursor;

    @Autowired
    BookRepository bookCursor;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        authorCursor.deleteAll();
        bookCursor.deleteAll();
    }

    @Test
    void getAllTest() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
        Author author = new Author();
        author.setName("Author 1");
        author = authorCursor.save(author);

        Author author2 = new Author();
        author2.setName("Author 2");
        author2 = authorCursor.save(author2);

        // Get Funcionando
        mockMvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Author 1"))
                .andExpect(jsonPath("$[1].name").value("Author 2"));

        // Deletando
        authorCursor.deleteAll();

        // Get Not Found
        mockMvc.perform(get("/api/author"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getByIdTest() throws Exception
    {
        Author author = new Author();
        author.setName("TestById");
        author = authorCursor.save(author);

        Book book1 = new Book();
        book1.setName("Book 1");
        book1.setAuthor(author);
        book1 = bookCursor.save(book1);

        Book book2 = new Book();
        book2.setName("Book 2");
        book2.setAuthor(author);
        book2 = bookCursor.save(book2);

        bookCursor.saveAll(List.of(book1, book2));

        // Get Author existe
        mockMvc.perform(get("/api/author/" + author.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("TestById"))
                .andExpect(jsonPath("$.books[0].name").value("Book 1"))
                .andExpect(jsonPath("$.books[1].name").value("Book 2"));

        // Get Not FOund
        mockMvc.perform(get("/api/author/" + 2))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void createAuthorTest() throws Exception {
        Author author = new Author();
        author.setName("TestByCreate");
        author = authorCursor.save(author);

        Book book1 = new Book();
        book1.setName("Book 1");
        book1.setAuthor(author);
        book1 = bookCursor.save(book1);

        Book book2 = new Book();
        book2.setName("Book 2");
        book2.setAuthor(author);
        book2 = bookCursor.save(book2);

        bookCursor.saveAll(List.of(book1, book2));

        // Post Salvando
        mockMvc.perform(post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk()) // Modificar para created
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Get Verificando
        mockMvc.perform(get("/api/author/" + author.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("TestByCreate"))
                .andExpect(jsonPath("$.books[0].name").value("Book 1"))
                .andExpect(jsonPath("$.books[1].name").value("Book 2"));
    }

//    @Test
//    void UpdateAuthorTest() throws Exception {
//        Author author = new Author();
//        author.setName("TestByCreate");
//        author = authorCursor.save(author);
//
//        Book book1 = new Book();
//        book1.setName("Book 1");
//        book1.setAuthor(author);
//        book1 = bookCursor.save(book1);
//
//        Book book2 = new Book();
//        book2.setName("Book 2");
//        book2.setAuthor(author);
//        book2 = bookCursor.save(book2);
//
//        bookCursor.saveAll(List.of(book1, book2));
//
//        mockMvc.perform(get("/api/author/" + author.getId()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("TestByCreate"))
//                .andExpect(jsonPath("$.books[0].name").value("Book 1"))
//                .andExpect(jsonPath("$.books[1].name").value("Book 2"));
//
//        author.setName("TestByUpdate");
//        author.setBooks(List.of(book1, book2));
//
//        mockMvc.perform(put("/api/author/" + author.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(author)))
//                        .andExpect(status().isOk()) // Modificar para created
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//        mockMvc.perform(get("/api/author/" + author.getId()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("TestByUpdate"))
//                .andExpect(jsonPath("$.books[0].name").value("Book 1"))
//                .andExpect(jsonPath("$.books[1].name").value("Book 2"));
//    }

    @Test
    void deleteAuthorTest() throws Exception
    {
        Author author = new Author();
        author.setName("TestById");
        author = authorCursor.save(author);

        Book book1 = new Book();
        book1.setName("Book 1");
        book1.setAuthor(author);
        book1 = bookCursor.save(book1);

        Book book2 = new Book();
        book2.setName("Book 2");
        book2.setAuthor(author);
        book2 = bookCursor.save(book2);

        bookCursor.saveAll(List.of(book1, book2));

        // Deletando
        mockMvc.perform(delete("/api/author/" + author.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Get Not found
        mockMvc.perform(get("/api/author/" + author.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
