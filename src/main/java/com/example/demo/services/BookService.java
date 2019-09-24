package com.example.demo.services;

import com.example.demo.entities.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).get();
    }

    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    public boolean deleteBook(Long bookId){
        Optional<Book> b = bookRepository.findById(bookId);
        if(b.isPresent()){
            bookRepository.delete(b.get());
            return true;
        }else{
            return false;
        }
    }

    public Book updateBook(Book book){
        return bookRepository.save(book);
}
}

