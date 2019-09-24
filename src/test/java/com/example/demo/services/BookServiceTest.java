package com.example.demo.services;

import com.example.demo.entities.Book;
import com.example.demo.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;



@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetAll() {
        Book book = new Book();
        List<Book> expectedBooks = Arrays.asList(book);
        doReturn(expectedBooks).when(bookRepository).findAll();
        List<Book> actualBook = bookService.getAll();
        assertThat(actualBook).isEqualTo(expectedBooks);

    }
    @Test
    public void testGetById() {
        Long bookid = Long.valueOf(6);
        Book book = new Book();
        Mockito.when(bookRepository.findById(bookid)).thenReturn(Optional.of(book));
        Book retrievedBook = bookService.getBookById(bookid);
        assertEquals(book,retrievedBook);
    }

    @Test
    public void testaddBook() {
        Book book = new Book();
        doReturn(book).when(bookRepository).save(book);
        Book createdbook = bookService.createBook(book);
        assertThat(book).isEqualTo(createdbook);
    }

    @Test
    public void testdelete() {
        Long bookid = Long.valueOf(4);
        Boolean deletedbook = bookService.deleteBook(bookid);
        assertNotNull(deletedbook);
        assertFalse(bookRepository.findById(bookid).isPresent());
    }

    @Test
    public void testupdate() {
        Book book = new Book();
        doReturn(book).when(bookRepository).save(book);
        Book updbook = bookService.updateBook(book);
        assertThat(book).isEqualTo(updbook);
    }
}
