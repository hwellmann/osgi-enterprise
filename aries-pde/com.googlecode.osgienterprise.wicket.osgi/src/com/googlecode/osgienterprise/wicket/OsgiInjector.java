package com.googlecode.osgienterprise.wicket;

import org.apache.wicket.injection.ConfigurableInjector;
import org.apache.wicket.injection.IFieldValueFactory;
import org.osgi.framework.BundleContext;

public class OsgiInjector extends ConfigurableInjector {
    IFieldValueFactory factory;

    /**
     * Constructor
     * 
     * @param locator
     *            spring context locator
     */
    public OsgiInjector(BundleContext locator)
    {
            this(locator, true);
    }


    /**
     * Constructor
     * 
     * @param locator
     *            spring context locator
     * @param wrapInProxies
     *            whether or not wicket should wrap dependencies with specialized proxies that can
     *            be safely serialized. in most cases this should be set to true.
     */
    public OsgiInjector(BundleContext locator, boolean wrapInProxies)
    {
            initFactory(locator, wrapInProxies);
    }

    private void initFactory(BundleContext locator, boolean wrapInProxies)
    {
            factory = new OsgiFieldValueFactory(locator, wrapInProxies);
    }

    @Override
    protected IFieldValueFactory getFieldValueFactory()
    {
            return factory;
    }
}
