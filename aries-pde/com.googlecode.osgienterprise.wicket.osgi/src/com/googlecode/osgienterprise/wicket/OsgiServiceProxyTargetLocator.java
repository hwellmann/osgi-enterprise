package com.googlecode.osgienterprise.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.proxy.IProxyTargetLocator;
import org.osgi.framework.BundleContext;

public class OsgiServiceProxyTargetLocator implements IProxyTargetLocator {

    private static final long serialVersionUID = 1L;
    private String className;

    public OsgiServiceProxyTargetLocator(String className) {
        this.className = className;
    }
    
    @Override
    public Object locateProxyTarget() {
        WebApplication application = (WebApplication) Application.get();
        BundleContext context = (BundleContext) application.getServletContext().getAttribute("osgi-bundlecontext");
        return OsgiServiceLookup.getOsgiService(context, className);
    }
}
