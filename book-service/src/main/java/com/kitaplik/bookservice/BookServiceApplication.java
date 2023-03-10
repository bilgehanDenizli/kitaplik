package com.kitaplik.bookservice;

import com.kitaplik.bookservice.model.Book;
import com.kitaplik.bookservice.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class BookServiceApplication implements CommandLineRunner {

    private final BookRepository bookRepository;

    public BookServiceApplication(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book("Dünyanın Gözü",2000,"Robert Jordan","Ithaki Yayınevi","123456");
        Book book2 = new Book("Yüzüklerin Efendisi",1960,"J.R.R Tolkien","Metis Yayınevi","123457");
        Book book3 = new Book("Harry Potter ve Felsefe Taşı",1997,"J.K Rowling","Cart curt Yayınevi","123458");

        bookRepository.saveAll(List.of(book1,book2,book3));

        List<Book> bookList = bookRepository.findAll();

        System.out.println(bookList);
    }
}
