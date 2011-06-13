/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.googlecode.osgienterprise.library.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

import com.googlecode.osgienterprise.library.client.BookService;
import com.googlecode.osgienterprise.library.model.Book;


/**
 * Simple integration test suite using OpenJPA in an OSGi context together with OSGi JPA and JNDI
 * services from Apache Aries.
 * 
 * The getOsgiService() methods are copied from Apache Aries integration tests and should be
 * factored out to a central utility class.
 * 
 * @author Harald Wellmann
 *
 */
@RunWith(JUnit4TestRunner.class)
public abstract class AbstractLibraryTest
{
    public static final String ARIES_VERSION = "0.3";
    public static final long DEFAULT_TIMEOUT = 10000;

    @Inject
    protected BundleContext bundleContext;
   
    @Test
    public void findDataSource() {
        getOsgiService(DataSource.class);
    }

    @Test
    public void findEntityManagerFactory() {
        getOsgiService(EntityManagerFactory.class, "(osgi.unit.name=library)",
                DEFAULT_TIMEOUT);
    }

    @Test
    public void findAndRunBookService() {
        BookService service = getOsgiService(BookService.class);
        service.createBooks();
        List<Book> books = service.findBooks();
        assertEquals(2, books.size());
    }

    protected <T> T getOsgiService(Class<T> type, long timeout) {
        return getOsgiService(type, null, timeout);
    }

    protected <T> T getOsgiService(Class<T> type) {
        return getOsgiService(type, null, DEFAULT_TIMEOUT);
    }

    protected <T> T getOsgiService(Class<T> type, String filter, long timeout) {
        return getOsgiService(null, type, filter, timeout);
    }

    protected <T> T getOsgiService(BundleContext bc, Class<T> type, String filter, long timeout) {
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
            tracker = new ServiceTracker(bc == null ? bundleContext : bc, osgiFilter, null);
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
