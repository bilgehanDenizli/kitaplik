package com.kitaplik.libraryservice.service;

import com.kitaplik.bookservice.BookId;
import com.kitaplik.bookservice.BookServiceGrpc;
import com.kitaplik.bookservice.Isbn;
import com.kitaplik.libraryservice.client.BookServiceClient;
import com.kitaplik.libraryservice.dto.AddBookRequest;
import com.kitaplik.libraryservice.dto.BookDto;
import com.kitaplik.libraryservice.dto.LibraryDto;
import com.kitaplik.libraryservice.exception.LibraryNotFoundException;
import com.kitaplik.libraryservice.model.Library;
import com.kitaplik.libraryservice.repository.LibraryRepository;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;

    @GrpcClient("book-service")
    private BookServiceGrpc.BookServiceBlockingStub bookServiceBlockingStub;

    public LibraryService(LibraryRepository libraryRepository, BookServiceClient bookServiceClient) {
        this.libraryRepository = libraryRepository;
        this.bookServiceClient = bookServiceClient;
    }

    public LibraryDto getAllBookInLibraryById(String id) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + id));

        /*LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBook()
                        .stream()
                        .map(book -> bookServiceClient.getBookById(book).getBody())
                        .map(ResponseEntity::getBody)
                        .collect(Collectors.toList()));*/
        List<BookDto> listBookDto = bookServiceClient.getBooksByIds(library.getUserBook()).getBody();
        LibraryDto libraryDto = new LibraryDto(library.getId(),listBookDto);


        return libraryDto;
    }

    public LibraryDto createLibrary(){
        Library newLibrary = libraryRepository.save(new Library());

        return new LibraryDto(newLibrary.getId());
    }

    public void addBookToLibrary(AddBookRequest addBookRequest){
        String bookId = bookServiceClient.getBookByIsbn(addBookRequest.getIsbn()).getBody().getId();

        Library library = libraryRepository.findById(addBookRequest.getId())
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + addBookRequest.getId()));

        library.getUserBook()
                .add(bookId);

        libraryRepository.save(library);

    }

    public void addBookToLibraryGrpc (AddBookRequest addBookRequest){
        BookId bookId = bookServiceBlockingStub.getBookByIsbn(Isbn.newBuilder().setIsbn(addBookRequest.getIsbn()).build());

        Library library = libraryRepository.findById(addBookRequest.getId())
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + addBookRequest.getId()));

        library.getUserBook()
                .add(bookId.getBookId());

        libraryRepository.save(library);

    }

    public List<String> getAllLibraries() {
        return libraryRepository.findAll()
                .stream()
                .map(l -> l.getId())
                .collect(Collectors.toList());
    }
}
