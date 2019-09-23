package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String name;
    private String genre;

    public Book(String name, String genre){
        this.genre = genre;
        this.name = name;
    }

    public Book(){}

    public Long getBookId(){
        return bookId;
    }
    public void setBookid(Long bookid){
        this.bookId = bookid;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }
}
