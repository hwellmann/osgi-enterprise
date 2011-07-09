package com.googlecode.osgienterprise.blog.wicket;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.osgienterprise.blog.api.BlogAuthor;
import com.googlecode.osgienterprise.blog.api.BloggingService;
import com.googlecode.osgienterprise.blog.wicket.bean.Author;

public class EditAuthorPage extends Layout {

    private static final long serialVersionUID = 1L;

    @Inject
    private BloggingService bloggingService;

    public class EditAuthorForm extends Form<Author> {

        private static final long serialVersionUID = 1L;

        public EditAuthorForm(String id, IModel<Author> model) {
            super(id, model);

            add(new FeedbackPanel("feedback"));
            add(new TextField<String>("fullName"));
            add(new TextField<String>("name"));
            
            TextField<String> emailField = new TextField<String>("emailAddress");
            emailField.setRequired(true);
            
            add(emailField);
            add(new TextField<String>("dateOfBirth"));
            add(new TextArea<String>("bio"));
        }

        @Override
        protected void onSubmit() {
            Author authorModel = getModelObject();
            BlogAuthor blogAuthor = bloggingService.getBlogAuthor(authorModel.getEmailAddress());
            if (blogAuthor == null) {
                bloggingService.createBlogAuthor(authorModel.getEmailAddress(),
                        authorModel.getName(), authorModel.getFullName(), authorModel.getBio(),
                        authorModel.getDateOfBirth());
            }
            else {
                bloggingService.updateBlogAuthor(authorModel.getEmailAddress(),
                        authorModel.getName(), authorModel.getFullName(), authorModel.getBio(),
                        authorModel.getDateOfBirth());
                
            }
            setResponsePage(new ViewAuthorPage(authorModel));
        }

    }

    public EditAuthorPage() {
        add(new Label("title", "Edit Author"));
        Author author = new Author();
        add(new EditAuthorForm("editAuthor", new CompoundPropertyModel<Author>(author)));
    }

    public EditAuthorPage(PageParameters params) {
        add(new Label("title", "Edit Author"));
        String email = params.get("email").toString();
        Author author = new Author();
        if (email != null) {
            BlogAuthor blogAuthor = bloggingService.getBlogAuthor(email);
            if (blogAuthor != null) {
                author.setName(blogAuthor.getName());
                author.setFullName(blogAuthor.getFullName());
                author.setDateOfBirth(blogAuthor.getDateOfBirth());
                author.setEmailAddress(email);
                author.setBio(blogAuthor.getBio());
            }
        }
        add(new EditAuthorForm("editAuthor", new CompoundPropertyModel<Author>(author)));
    }
}
