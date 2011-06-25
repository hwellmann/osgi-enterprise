package com.googlecode.osgienterprise.wicket;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.protocol.http.WebApplication;
import org.osgi.framework.BundleContext;

public class OsgiServiceInjector implements IComponentInstantiationListener {
    
    private BundleContext bundleContext;

    public OsgiServiceInjector(WebApplication application) {
        this.bundleContext = (BundleContext) application.getServletContext().getAttribute("osgi-bundlecontext");
    }

    @Override
    public void onInstantiation(Component component) {
        for (Field field : component.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Inject.class) != null) {
                injectOsgiService(component, field);
            }
        }
    }

    private void injectOsgiService(Component component, Field field) {
        Object service = ServiceLookup.getOsgiService(bundleContext, field.getType());
        try {
            if (field.isAccessible()) {
                field.set(component, service);
            }
            else {
                field.setAccessible(true);
                field.set(component, service);
                field.setAccessible(false);
            }
        }
        catch (IllegalAccessException exc) {
            throw new WicketRuntimeException(exc);
        }       
    }

}
