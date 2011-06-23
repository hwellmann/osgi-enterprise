package com.googlecode.osgienterprise.ariespde.library.web;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import com.googlecode.osgienterprise.wicket.OsgiClassResolver;
import com.googlecode.osgienterprise.wicket.OsgiServiceInjector;

public class LibraryApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        addComponentInstantiationListener(new OsgiServiceInjector(this));
        
        getApplicationSettings().setClassResolver(new OsgiClassResolver());
    }
    
    @Override
    public Class<? extends Page> getHomePage() {
        return AddAuthorPage.class;
    }

}
