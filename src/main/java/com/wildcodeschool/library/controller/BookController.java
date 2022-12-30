package com.wildcodeschool.library.controller;

import com.wildcodeschool.library.entity.Book;
import com.wildcodeschool.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books")
        public List<Book> index() {
       return bookRepository.findAll();
    }

    @PostMapping("/book/search")
    public List<Book> postBook(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        List<Book> books = new ArrayList<>();
        return bookRepository.searchBook(searchTerm);
    }

    @PostMapping("/book")
    public Book create(@RequestBody Map<String, String> body){
        String title = body.get("title");
        String author = body.get("author");
        String description = body.get("description");
        Book requestBook = new Book(null, title,author,description);
        return bookRepository.save(requestBook);
    }

    @PutMapping("/book/{id}")
    public Book update(@PathVariable Long id, @RequestBody Map<String, String> body){
        Long bookId = id;
        String title = body.get("title");
        String author = body.get("author");
        String description = body.get("description");
        Book requestBook = new Book(bookId,title,author, description);
        return bookRepository.update(requestBook);
    }

    @DeleteMapping("book/{id}")
    public boolean delete(@PathVariable Long id){
         bookRepository.deleteById(id);
        return true;

    }
}
