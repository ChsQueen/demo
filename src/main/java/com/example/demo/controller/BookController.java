package com.example.demo.controller;

import com.example.demo.entities.Book;
import com.example.demo.services.BookService;
import com.sun.org.apache.xpath.internal.operations.String;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.annotations.common.util.StringHelper.isNotEmpty;
import static org.springframework.util.StringUtils.*;


@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }


    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        Book b = bookService.getBookById(book.getBookId());
        if (book != null) {
            return bookService.updateBook(book);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public Book patchBook(@PathVariable Long id, @RequestBody Book changes) {
       Book b = bookService.getBookById(id);

       //if (isNotEmpty(changes.getName())) b.setName(changes.getName());
       if (isNotEmpty(changes.getGenre())) b.setGenre(changes.getGenre());

        return bookService.updateBook(b);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id){
        if (bookService.deleteBook(id))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.valueOf(500));
    }

}