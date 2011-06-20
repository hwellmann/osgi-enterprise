package com.googlecode.osgienterprise.ariespde.library.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.osgienterprise.ariespde.library.service.LibraryService;

public class LibraryClient {
    private Logger log = LoggerFactory.getLogger(LibraryClient.class);

    private LibraryService libraryService;

    private AuthorCreator authorCreator;

    public LibraryClient() {
        log.info("initializing LibraryClient");
    }

    public void searchLibrary() {
        libraryService.fillLibrary();
        long numBooks = libraryService.getNumBooks();
        log.info("The library has {} books", numBooks);

        authorCreator.createAuthor("Charles", "Dickens");
    }

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void setAuthorCreator(AuthorCreator authorCreator) {
        this.authorCreator = authorCreator;
    }
    
    
}
