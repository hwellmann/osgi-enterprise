package com.googlecode.osgienterprise.wicket;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.proxy.LazyInitProxyFactory;
import org.osgi.framework.BundleContext;

public class OsgiFieldValueFactory implements IFieldValueFactory {
    
    private final BundleContext context;
    private final boolean wrapInProxies;

    /**
     * @param contextLocator
     *            spring context locator
     */
    public OsgiFieldValueFactory(BundleContext context) {
        this(context, true);
    }

    /**
     * @param contextLocator
     *            spring context locator
     * @param wrapInProxies
     *            whether or not wicket should wrap dependencies with specialized proxies that can
     *            be safely serialized. in most cases this should be set to true.
     */
    public OsgiFieldValueFactory(BundleContext context, boolean wrapInProxies) {
        if (context == null) {
            throw new IllegalArgumentException("[context] argument cannot be null");
        }
        this.context = context;
        this.wrapInProxies = wrapInProxies;
    }

    /**
     * @see org.apache.wicket.injection.IFieldValueFactory#getFieldValue(java.lang.reflect.Field,
     *      java.lang.Object)
     */
    public Object getFieldValue(Field field, Object fieldOwner) {
        if (field.isAnnotationPresent(Inject.class)) {
            OsgiServiceLocator locator = new OsgiServiceLocator(field.getType().getName());


            final Object target;
            if (wrapInProxies) {
                target = LazyInitProxyFactory.createProxy(field.getType(), locator);
            }
            else {
                target = locator.locateProxyTarget();
            }

            return target;
        }
        return null;
    }

    /**
     * @see org.apache.wicket.injection.IFieldValueFactory#supportsField(java.lang.reflect.Field)
     */
    public boolean supportsField(Field field) {
        return field.isAnnotationPresent(Inject.class);
    }
}
