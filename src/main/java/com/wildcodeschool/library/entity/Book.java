package com.wildcodeschool.library.entity;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String description;

    public Book(){
    }

    public Book(Long id, String title, String author, String description){
        this.id=id;
        this.title= title;
        this.author=author;
        this.description=description;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
