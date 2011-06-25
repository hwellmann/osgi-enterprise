package com.googlecode.osgienterprise.wicket;

import org.apache.wicket.injection.ConfigurableInjector;
import org.apache.wicket.injection.IFieldValueFactory;

public class OsgiInjector extends ConfigurableInjector {
    IFieldValueFactory factory;

    public OsgiInjector()
    {
            this(true);
    }


    public OsgiInjector(boolean wrapInProxies)
    {
            initFactory(wrapInProxies);
    }

    private void initFactory(boolean wrapInProxies)
    {
            factory = new OsgiFieldValueFactory(wrapInProxies);
    }

    @Override
    protected IFieldValueFactory getFieldValueFactory()
    {
            return factory;
    }
}
