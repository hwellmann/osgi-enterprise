package com.googlecode.osgienterprise.geminitx.library.client;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.osgienterprise.geminitx.library.service.LibraryService;

public class LibraryClient {
    private Logger log = LoggerFactory.getLogger(LibraryClient.class);

    @Inject
    private LibraryService libraryService;

    @Inject
    private AuthorCreator authorCreator;

    public LibraryClient() {
        log.info("initializing LibraryClient");
    }

    @PostConstruct
    public void searchLibrary() {
        libraryService.fillLibrary();
        long numBooks = libraryService.getNumBooks();
        log.info("The library has {} books", numBooks);

        authorCreator.createAuthor("Charles", "Dickens");
    }
}
