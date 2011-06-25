package com.googlecode.osgienterprise.wicket;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.osgi.framework.BundleContext;

public class OsgiWebApplicationFactory implements IWebApplicationFactory {
    
    public static final String APPLICATION_NAME_KEY = "wicket.osgi.application.name";

    @Override
    public WebApplication createApplication(WicketFilter filter) {
        ServletContext servletContext = filter.getFilterConfig().getServletContext();
        BundleContext bundleContext = (BundleContext) servletContext.getAttribute("osgi-bundlecontext");
        
        String appName = filter.getFilterConfig().getInitParameter(APPLICATION_NAME_KEY);
        Map<String,String> props = new LinkedHashMap<String, String>(1);
        props.put(APPLICATION_NAME_KEY, appName);
        
        WebApplication webApplication = OsgiServiceLookup.getOsgiService(bundleContext, WebApplication.class, props);
        return webApplication;
    }

}
