package com.googlecode.osgienterprise.blog.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.osgienterprise.blog.api.BlogEntry;

public class BlogEntryPanel extends Panel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public BlogEntryPanel(String id, IModel<BlogEntry> model) {
        super(id, model);
        add(new Label("publishDate"));
        add(new Label("title"));
        Label body = new Label("body");
        body.setEscapeModelStrings(false);
        add(body);
        PageParameters params = new PageParameters();
        params.set("email", model.getObject().getAuthorEmail());
        Link<Page> link = new BookmarkablePageLink<Page>("authorEmail", ViewAuthorPage.class, params);                
        link.add(new Label("author.name"));
        add(link);
    }

}
