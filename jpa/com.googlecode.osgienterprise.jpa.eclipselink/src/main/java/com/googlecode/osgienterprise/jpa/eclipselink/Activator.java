package com.googlecode.osgienterprise.jpa.eclipselink;

import java.util.Hashtable;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public static final String PERSISTENCE_PROVIDER = "javax.persistence.provider";
    public static final String ECLIPSELINK_OSGI_PROVIDER = "org.eclipse.persistence.jpa.PersistenceProvider";
    private static BundleContext context;

    
    @Override
    public void start(BundleContext context) throws Exception {
        Activator.context = context;
        registerProviderService();
    }
    
    /**
     * Our service provider provides the javax.persistence.spi.PersistenceProvider service. In this
     * method, we register as a provider of that service
     * 
     * @throws Exception
     */
    public void registerProviderService() throws Exception {
        // Create and register ourselves as a JPA persistence provider service
        PersistenceProvider providerService = new PersistenceProvider();
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(PERSISTENCE_PROVIDER, ECLIPSELINK_OSGI_PROVIDER);
        context.registerService("javax.persistence.spi.PersistenceProvider", providerService, props);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}
