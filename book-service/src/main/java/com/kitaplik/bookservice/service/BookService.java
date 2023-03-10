package com.kitaplik.bookservice.service;

import com.kitaplik.bookservice.dto.BookDto;
import com.kitaplik.bookservice.dto.BookIdDto;
import com.kitaplik.bookservice.exception.BookNotFoundException;
import com.kitaplik.bookservice.model.Book;
import com.kitaplik.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks(){
        return bookRepository
                .findAll()
                .stream()
                .map(BookDto::convert)
                .collect(Collectors.toList());
    }

    public BookIdDto findByIsbn(String isbn){
        return bookRepository
                .getBookByIsbn(isbn)
                .map(book -> new BookIdDto(book.getId(),book.getIsbn()))
                .orElseThrow(() -> new BookNotFoundException("Book could not found by isbn:" + isbn));
    }

    public BookDto findBookDetailsById(String id){
        return bookRepository.findById(id)
                .map(BookDto::convert)
                .orElseThrow(() -> new BookNotFoundException("Book could not found by id:" + id));
    }

    public void addNewBook(Book book) {
        bookRepository.save(new Book(book.getTitle(),book.getBookYear(),book.getAuthor(),book.getPressName(),book.getIsbn()));
    }

    public BookDto findBooksByTitle(String title) {
        return bookRepository.getBooksByTitleOrderById(title)
                .map(book -> new BookDto(book.getId(),book.getTitle(),book.getAuthor(),book.getBookYear(),book.getPressName(),book.getIsbn()))
                .orElseThrow(() -> new BookNotFoundException("Book could not found by title:" + title));
    }

    public List<BookDto> getBooksByIds(List<String> bookList) {
        return bookRepository.findBookByIdIn(bookList)
                .stream()
                .map(BookDto::convert)
                .collect(Collectors.toList());
    }
}
