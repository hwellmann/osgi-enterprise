package com.googlecode.osgienterprise.blog.wicket;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.osgienterprise.blog.api.BloggingService;
import com.googlecode.osgienterprise.blog.wicket.bean.Author;

public class ViewAuthorPage extends Layout {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private BloggingService bloggingService;
    
    public ViewAuthorPage() {
        add(new Label("title", bloggingService.getBlogTitle()));
    }

    public ViewAuthorPage(final Author author) {
        add(new Label("title", bloggingService.getBlogTitle()));
        CompoundPropertyModel<Author> model = new CompoundPropertyModel<Author>(author);
        setDefaultModel(model);
        add(new Label("fullName"));
        add(new Label("name"));
        add(new Label("emailAddress"));
        add(new Label("dateOfBirth"));
        add(new Label("bio"));
        
        add(new Link<Page>("editAuthor"){

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                PageParameters params = new PageParameters();
                params.set("email", author.getEmailAddress());
                setResponsePage(EditAuthorPage.class, params);
            }
            
        });
    }

}
