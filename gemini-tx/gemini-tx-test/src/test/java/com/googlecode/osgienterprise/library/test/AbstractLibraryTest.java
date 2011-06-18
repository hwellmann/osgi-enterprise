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
package com.googlecode.osgienterprise.library.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;

import com.googlecode.osgienterprise.geminitx.library.client.LibraryClient;
import com.googlecode.osgienterprise.geminitx.library.model.Book;


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
    public static final String OSGI_ENTERPRISE_VERSION = "0.0.1-SNAPSHOT";

    @Test
    public void findDataSource(BundleContext bc) {
        ServiceLookup.getOsgiService(bc, DataSource.class);
    }

    @Test
    public void findEntityManagerFactory(BundleContext bc) {
        ServiceLookup.getOsgiService(bc, EntityManagerFactory.class, "(osgi.unit.name=library)");
    }

    @Test
    public void findAndRunLibraryClient(BundleContext bc) {
        LibraryClient service = ServiceLookup.getOsgiService(bc, LibraryClient.class);
        service.searchLibrary();
    }
}
