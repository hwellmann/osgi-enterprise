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

import org.apache.wicket.WicketRuntimeException;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class ServiceLookup {

    public static final long DEFAULT_TIMEOUT = 10000;

    public static <T> T getOsgiService(BundleContext bc, Class<T> type) {
        return getOsgiService(bc, type, DEFAULT_TIMEOUT);
    }

    public static <T> T getOsgiService(BundleContext bc, Class<T> type, long timeout) {
        return getOsgiService(bc, type.getName(), timeout);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOsgiService(BundleContext bc, String className, long timeout) {
        ServiceTracker tracker = new ServiceTracker(bc, className, null);
        try {
            tracker.open();
            Object svc = tracker.waitForService(timeout);
            if (svc == null) {
                throw new WicketRuntimeException("Gave up waiting for service " + className);
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

    public static <T> T getOsgiService(BundleContext bc, String className) {
        return getOsgiService(bc, className, DEFAULT_TIMEOUT);
    }

}
