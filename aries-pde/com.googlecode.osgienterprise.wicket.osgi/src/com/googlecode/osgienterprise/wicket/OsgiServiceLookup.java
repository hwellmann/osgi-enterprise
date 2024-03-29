/*
 * Copyright 2011 Harald Wellmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.osgienterprise.wicket;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.WicketRuntimeException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

public class OsgiServiceLookup {

    public static final long DEFAULT_TIMEOUT = 10000;

    public static <T> T getOsgiService(BundleContext bc, String className) {
        return getOsgiService(bc, className, DEFAULT_TIMEOUT, null);
    }

    public static <T> T getOsgiService(BundleContext bc, Class<T> type) {
        return getOsgiService(bc, type, DEFAULT_TIMEOUT);
    }

    public static <T> T getOsgiService(BundleContext bc, Class<T> type, Map<String, String> props) {
        return getOsgiService(bc, type, DEFAULT_TIMEOUT, props);
    }

    public static <T> T getOsgiService(BundleContext bc, Class<T> type, long timeout, Map<String, String> props) {
        return getOsgiService(bc, type.getName(), timeout, props);
    }

    public static <T> T getOsgiService(BundleContext bc, Class<T> type, long timeout) {
        return getOsgiService(bc, type.getName(), timeout, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOsgiService(BundleContext bc, String className, long timeout, Map<String, String> props) {
        ServiceTracker tracker = createServiceTracker(bc, className, props);
        try {
            tracker.open();
            Object svc = tracker.waitForService(timeout);
            if (svc == null) {
                throw new WicketRuntimeException("gave up waiting for service " + className);
            }
            return (T) svc;
        }
        catch (InterruptedException exc) {
            throw new WicketRuntimeException(exc);
        }
        finally {
            tracker.close();
        }
    }
        

    private static ServiceTracker createServiceTracker(BundleContext bc, String className, Map<String, String> props) {
        if (props == null || props.isEmpty()) {
            return new ServiceTracker(bc, className, null);
        }
        
        StringBuilder builder = new StringBuilder("(&(objectClass=");
        builder.append(className);
        builder.append(')');
        for (Entry<String, String> entry : props.entrySet()) {
            builder.append('(');
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
            builder.append(')');
        }
        builder.append(')');
        try {
            Filter filter;
            filter = FrameworkUtil.createFilter(builder.toString());
            ServiceTracker tracker = new ServiceTracker(bc, filter, null);
            return tracker;
        }
        catch (InvalidSyntaxException exc) {
           throw new WicketRuntimeException(exc);
        }
    }
}
