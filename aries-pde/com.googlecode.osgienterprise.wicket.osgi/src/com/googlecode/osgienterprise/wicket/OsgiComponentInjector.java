package com.googlecode.osgienterprise.wicket;

import org.apache.wicket.injection.ComponentInjector;
import org.apache.wicket.injection.web.InjectorHolder;

public class OsgiComponentInjector extends ComponentInjector {

    public OsgiComponentInjector() {
        this(true);
    }

    public OsgiComponentInjector(boolean wrapInProxies) {
        InjectorHolder.setInjector(new OsgiInjector(wrapInProxies));
    }
}
