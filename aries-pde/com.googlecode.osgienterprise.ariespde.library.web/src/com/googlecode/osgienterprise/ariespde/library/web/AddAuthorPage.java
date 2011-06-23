package com.googlecode.osgienterprise.ariespde.library.web;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.googlecode.osgienterprise.ariespde.library.model.Author;
import com.googlecode.osgienterprise.ariespde.library.service.LibraryService;

public class AddAuthorPage extends WebPage {
    
    @Inject
    private LibraryService libraryService;
    
    public class AddAuthorForm extends Form<Author> {

        public AddAuthorForm(String id, IModel<Author> model) {
            super(id, model);
            
            add(new TextField<Author>("firstName"));
            add(new TextField<Author>("lastName"));
            add (new Button("submit"));
        }
        
        @Override
        protected void onSubmit() {
            Author author = getModelObject();
            libraryService.createAuthor(author);
            setResponsePage(BooksPage.class);
        }

    }
    
    public AddAuthorPage() {
        Author author = new Author();
        
        add(new AddAuthorForm("addAuthor", new CompoundPropertyModel<Author>(author)));
    }

}
