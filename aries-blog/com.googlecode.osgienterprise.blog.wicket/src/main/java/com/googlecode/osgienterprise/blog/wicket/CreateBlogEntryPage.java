package com.googlecode.osgienterprise.blog.wicket;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.googlecode.osgienterprise.blog.api.BlogAuthor;
import com.googlecode.osgienterprise.blog.api.BloggingService;
import com.googlecode.osgienterprise.blog.wicket.bean.Post;

public class CreateBlogEntryPage extends Layout {

    private static final long serialVersionUID = 1L;

    @Inject
    private BloggingService bloggingService;

    public class CreateBlogEntryForm extends Form<Post> {

        private static final long serialVersionUID = 1L;

        public CreateBlogEntryForm(String id, IModel<Post> model) {
            super(id, model);
            
            add(new FeedbackPanel("feedback"));

            add(new TextField<String>("title"));
            TextArea<String> textArea = new TextArea<String>("text");
            //textArea.setEscapeModelStrings(false);
            add(textArea);
            
            TextField<String> emailField = new TextField<String>("email");
            emailField.setRequired(true);            
            add(emailField);
            
            add(new TextField<String>("tags"));
        }

        @Override
        protected void onSubmit() {
            Post post = getModelObject();
            BlogAuthor blogAuthor = bloggingService.getBlogAuthor(post.getEmail());
            if (blogAuthor != null) {
                bloggingService.createBlogEntry(post.getEmail(), post.getTitle(), post.getText(), post.getTags());
                setResponsePage(ViewBlogPage.class);
            }
            else {
                error("No author for email " + post.getEmail());
            }
        }

    }

    public CreateBlogEntryPage() {
        add(new Label("title", "Create New Post"));
        Post post = new Post();
        add(new CreateBlogEntryForm("createPost", new CompoundPropertyModel<Post>(post)));
    }
}
