package com.googlecode.osgienterprise.wicket;

import org.apache.wicket.injection.ComponentInjector;
import org.apache.wicket.injection.web.InjectorHolder;
import org.osgi.framework.BundleContext;

public class OsgiComponentInjector extends ComponentInjector {

    public OsgiComponentInjector(BundleContext context) {
        this(context, true);
    }

    public OsgiComponentInjector(BundleContext context,
            boolean wrapInProxies) {

        if (context == null)
        {
                throw new IllegalArgumentException("Argument [[context]] cannot be null");
        }

        // ... and create and register the annotation aware injector
        InjectorHolder.setInjector(new OsgiInjector(context, wrapInProxies));
    }
}
