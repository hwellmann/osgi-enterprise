package com.googlecode.osgienterprise.wicket;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.osgi.framework.BundleContext;

public class OsgiWebApplicationFactory implements IWebApplicationFactory {

    @Override
    public WebApplication createApplication(WicketFilter filter) {
        ServletContext servletContext = filter.getFilterConfig().getServletContext();
        BundleContext bundleContext = (BundleContext) servletContext.getAttribute("osgi-bundlecontext");
        WebApplication webApplication = ServiceLookup.getOsgiService(bundleContext, WebApplication.class);
        return webApplication;
    }

}
