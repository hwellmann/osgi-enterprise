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
