package com.googlecode.osgienterprise.ariespde.library.web;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.osgi.framework.BundleContext;

import com.googlecode.osgienterprise.wicket.OsgiClassResolver;
import com.googlecode.osgienterprise.wicket.OsgiComponentInjector;

public class LibraryApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        BundleContext bc = (BundleContext) getServletContext().getAttribute("osgi-bundlecontext");
        addComponentInstantiationListener(new OsgiComponentInjector(bc));
        //addComponentInstantiationListener(new OsgiServiceInjector(this));
        
        /*
         * Not really needed, at least on Jetty. 
         */
        getApplicationSettings().setClassResolver(new OsgiClassResolver());
    }
    
    @Override
    public Class<? extends Page> getHomePage() {
        return AddAuthorPage.class;
    }

}
