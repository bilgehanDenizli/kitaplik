package com.kitaplik.libraryservice.client;

import com.kitaplik.libraryservice.dto.BookDto;
import com.kitaplik.libraryservice.dto.BookIdDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book-service",path = "/v1/book")
public interface BookServiceClient {

    Logger LOGGER = LoggerFactory.getLogger(BookServiceClient.class);

    @GetMapping("/isbn/{isbn}")
    @CircuitBreaker(name = "getBookByIsbnCircuitBreaker",fallbackMethod = "getBookFallback")
    ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable(value = "isbn") String isbn);

    default ResponseEntity<BookIdDto> getBookFallback(String isbn, Exception exception){
        LOGGER.info("Book not found by isbn: " + isbn + ", returning default BookDto object.");
        return ResponseEntity.ok(new BookIdDto("default-book","default-isbn"));
    }

    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "getBookByIdCircuitBreaker",fallbackMethod = "getBookByIdFallback")
    ResponseEntity<BookDto> getBookById(@PathVariable(value = "id") String id);

    default ResponseEntity<BookDto> getBookByIdFallback (String id,Exception exception){
        LOGGER.info("Book not found by id: " + id + ", returning default BookDto object.");
        return ResponseEntity.ok(new BookDto("default-book","","",0,"","default-isbn"));
    }

    @GetMapping("/booksByIds")
    ResponseEntity<List<BookDto>> getBooksByIds(@RequestParam List<String> bookList);


}
