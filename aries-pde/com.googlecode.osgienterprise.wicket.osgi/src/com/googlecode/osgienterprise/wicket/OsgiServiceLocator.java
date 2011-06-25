package com.googlecode.osgienterprise.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.proxy.IProxyTargetLocator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class OsgiServiceLocator implements IProxyTargetLocator {

    private static final long serialVersionUID = 1L;
    private static final int TIMEOUT = 10000;
    private String className;

    public OsgiServiceLocator(String className) {
        this.className = className;
    }
    
    @Override
    public Object locateProxyTarget() {
        WebApplication application = (WebApplication) Application.get();
        BundleContext context = (BundleContext) application.getServletContext().getAttribute("osgi-bundlecontext");
        ServiceTracker tracker = new ServiceTracker(context, className, null);
        Object service;
        try {
            tracker.open();
            service = tracker.waitForService(TIMEOUT);
            return service;
        }
        catch (InterruptedException exc) {
            throw new WicketRuntimeException(exc);
        }
        finally {
            tracker.close();            
        }
    }

}
