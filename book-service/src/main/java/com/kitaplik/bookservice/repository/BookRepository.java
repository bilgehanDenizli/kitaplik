package com.kitaplik.bookservice.repository;

import com.kitaplik.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> getBookByIsbn(String isbn);
    Optional<Book> getBooksByTitleOrderById(String title);

    /*@Query( "select b from books b where bookList in :ids" )
    List<Book> getBooksById(@Param("ids") List<String> bookList);*/

    List<Book> findBookByIdIn(List<String> bookList);

}
