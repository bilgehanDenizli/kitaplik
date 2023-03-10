package com.kitaplik.libraryservice.controller;


import com.kitaplik.libraryservice.dto.AddBookRequest;
import com.kitaplik.libraryservice.dto.LibraryDto;
import com.kitaplik.libraryservice.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library")
public class LibraryController {

    Logger logger = LoggerFactory.getLogger(LibraryController.class);
    private LibraryService libraryService;
    private Environment environment;

    @Value("${library-service.count}")
    private Integer count;

    public LibraryController(LibraryService libraryService,Environment environment) {
        this.libraryService = libraryService;
        this.environment = environment;
    }


    @GetMapping("/getLibrary")
    public ResponseEntity<LibraryDto> getLibraryById(@RequestParam String id) {
        return ResponseEntity.ok(libraryService.getAllBookInLibraryById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<LibraryDto> createLibrary(){
        logger.info("Library created on port number " + environment.getProperty("local.server.port"));
        return ResponseEntity.ok(libraryService.createLibrary());
    }

    @PutMapping("/addBookToLibrary") ResponseEntity<Void> addBookToLibrary(@RequestBody AddBookRequest addBookRequest){
        libraryService.addBookToLibrary(addBookRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllLibraries")
    public ResponseEntity<List<String>> getAllLibraries() {
        return ResponseEntity.ok(libraryService.getAllLibraries());
    }

    @GetMapping("/getCount")
    public ResponseEntity<String> getCount() {
        return ResponseEntity.ok("Library service count: " + count);
    }

}
