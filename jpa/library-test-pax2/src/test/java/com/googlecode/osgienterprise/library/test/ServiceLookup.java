package com.googlecode.osgienterprise.library.test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

public class ServiceLookup {

    public static final long DEFAULT_TIMEOUT = 10000;

    public static <T> T getOsgiService(BundleContext bc, Class<T> type, long timeout) {
        return getOsgiService(bc, type, null, timeout);
    }

    public static <T> T getOsgiService(BundleContext bc, Class<T> type) {
        return getOsgiService(bc, type, null, DEFAULT_TIMEOUT);
    }

    public static <T> T getOsgiService(BundleContext bc, Class<T> type, String filter) {
        return getOsgiService(bc, type, filter, DEFAULT_TIMEOUT);
    }

    public static <T> T getOsgiService(BundleContext bc, Class<T> type, String filter, long timeout) {
        ServiceTracker tracker = null;
        try {
            String flt;
            if (filter != null) {
                if (filter.startsWith("(")) {
                    flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")" + filter + ")";
                }
                else {
                    flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")(" + filter + "))";
                }
            }
            else {
                flt = "(" + Constants.OBJECTCLASS + "=" + type.getName() + ")";
            }
            Filter osgiFilter = FrameworkUtil.createFilter(flt);
            tracker = new ServiceTracker(bc, osgiFilter, null);
            tracker.open();
            Object svc = type.cast(tracker.waitForService(timeout));
            if (svc == null) {
                throw new RuntimeException("Gave up waiting for service " + flt);
            }
            return type.cast(svc);
        }
        catch (InvalidSyntaxException e) {
            throw new IllegalArgumentException("Invalid filter", e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
