package com.googlecode.osgienterprise.blog.wicket;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import com.googlecode.osgienterprise.blog.api.BlogEntry;
import com.googlecode.osgienterprise.blog.api.BloggingService;

public class ViewBlogPage extends Layout {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private BloggingService bloggingService;
    
    public ViewBlogPage() {
        List<? extends BlogEntry> posts = bloggingService.getAllBlogEntries();
        
        
        PropertyListView<BlogEntry> listView = new PropertyListView<BlogEntry>("posts", posts) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<BlogEntry> item) {
                item.add(new BlogEntryPanel("post", item.getModel()));
            }            
        };
        
        add(listView);
    }

    @Override
    protected String getPageTitle() {
        return bloggingService.getBlogTitle();
    }
}
