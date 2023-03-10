package com.kitaplik.bookservice.controller;

import com.kitaplik.bookservice.dto.BookDto;
import com.kitaplik.bookservice.dto.BookIdDto;
import com.kitaplik.bookservice.model.Book;
import com.kitaplik.bookservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Request;
import java.util.List;

@RestController
@RequestMapping("/v1/book")
public class BookController {

    private final BookService bookService;
    private Request request;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBook() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable @NotEmpty String isbn) {
        return ResponseEntity.ok(bookService.findByIsbn(isbn));
    }

    @GetMapping("/id{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable @NotEmpty String id) {
        return ResponseEntity.ok(bookService.findBookDetailsById(id));
    }

    @GetMapping("/booksByIds")
    public ResponseEntity<List<BookDto>> getBooksByIds(@RequestParam List<String> bookList){
        return ResponseEntity.ok(bookService.getBooksByIds(bookList));
    }

    @PostMapping("/newBook")
    public ResponseEntity<Void> addNewBook(@RequestBody Book book){
        bookService.addNewBook(book);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findBooksByTitle")
    public ResponseEntity<BookDto> booksByTitle(@FormParam("title") @NotEmpty String title){
        return ResponseEntity.ok(bookService.findBooksByTitle(title));
    }
}
