package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.services.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import services.DemoApplicationTests;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional

public class BookControllerTests {
    @Autowired
    MockMvc mvc;

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    private List<Book> books = new ArrayList<>();

    @Before
    public void setUp() throws Exception{

        for (int i = 0; i < 10; i++){
            books.add(new Book("name"+i, "genre"+i));
        }
        for (Book book : bookRepository.saveAll(books)) {

        }
        ;
    }
     @Test
    public void getAllBooks() throws Exception { mvc.perform(get("/books/all"))
                 .andExpect(status().isOk());
    }

    @Test
    public void createBook() throws Exception {

        mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
        .content("{ \"name\": \"NAME\",\"genre\":\"GENRE\"}"))
                .andExpect(status().isOk());
    }
    @Test
    public void updateBookWithPut() throws Exception {
        Book  book = books.get(3);
        book.setGenre("Genre is totally fine");
        String json = String.format("{ \"bookId\": \"%s\", \"name\": \"%s\", \"genre\": \"%s\" }",
                book.getBookId(), book.getName(),book.getGenre());
        mvc.perform(put("/books/"+book.getBookId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookId").value(book.getBookId()))
                .andExpect(jsonPath("genre").value("Genre is totally fine"));
    }

    @Test
    public void updateBookWithPatch() throws Exception {
        Book book = books.get(5);
        String newName = "Updated Name";
        String newGenre= "Updated Genre";
        String jsonData = String.format("{\"name:\": \"%s\", \"genre\": \"%s\" }",newName, newGenre);
        mvc.perform(patch("/books/"+book.getBookId()).contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andExpect(status().isOk())
             //   .andExpect(jsonPath("name").value(newName))
                .andExpect(jsonPath("genre").value(newGenre));
    }

    @Test
    public void deleteBook() throws Exception {
        long id = books.get(8).getBookId();
        mvc.perform(delete("/books/"+id))
                .andExpect(status().isOk());
        assertFalse(bookRepository.findById(id).isPresent());
    }
}