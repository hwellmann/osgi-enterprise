package com.googlecode.osgienterprise.ariespde.library.web;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import com.googlecode.osgienterprise.ariespde.library.service.LibraryService;

public class BooksPage extends WebPage {
    
    @Inject
    private LibraryService libraryService;
    
    public BooksPage() {
        add(new Label("numBooks", Long.toString(libraryService.getNumBooks())));
        add(new Label("numAuthors", Long.toString(libraryService.getNumAuthors())));
    }

}
