package com.example.demo.services;

import com.example.demo.entities.Book;
import com.example.demo.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetAll() {
        for (Book book : bookRepository.findAll()) {
        verify(bookService).getAll();
        }
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
        book.setName("test addname");
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book createdbook = bookService.createBook(book);
        assertThat(book.getName()).isSameAs(createdbook.getName());
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
        book.setName("test updname");
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(new Book());
        Book updbook = bookService.updateBook(book);
        assertThat(book.getBookId()).isSameAs(updbook.getBookId());
    }
}
