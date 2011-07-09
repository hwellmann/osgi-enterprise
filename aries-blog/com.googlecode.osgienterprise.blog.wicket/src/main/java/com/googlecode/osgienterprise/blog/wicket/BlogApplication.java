package com.googlecode.osgienterprise.blog.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.osgi.inject.OsgiComponentInjector;


public class BlogApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        getComponentInstantiationListeners().add(new OsgiComponentInjector());
        
        mountPage("/editAuthor", EditAuthorPage.class);
        mountPage("/createBlogEntry", CreateBlogEntryPage.class);
    }
    
    @Override
    public Class<? extends Page> getHomePage() {
        return ViewBlogPage.class;
    }
}
