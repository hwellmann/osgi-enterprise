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

import static org.ops4j.pax.exam.CoreOptions.equinox;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.keepCaches;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.workingDirectory;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;


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
public class Eclipselink_2_2_0_OsgiTest extends AbstractLibraryTest
{
    public static final String ECLIPSELINK_VERSION = "2.2.0";

    @Configuration
    public static Option[] configuration() {
        Option[] options = options(
              

        // Bundles under test
        mavenBundle("com.googlecode.osgi-enterprise.jpa", "com.googlecode.osgienterprise.library.datasource", OSGI_ENTERPRISE_VERSION),
        mavenBundle("com.googlecode.osgi-enterprise.jpa", "com.googlecode.osgienterprise.library.model", OSGI_ENTERPRISE_VERSION),
        mavenBundle("com.googlecode.osgi-enterprise.jpa", "com.googlecode.osgienterprise.library.client", OSGI_ENTERPRISE_VERSION),
        mavenBundle("com.googlecode.osgi-enterprise.jpa", "com.googlecode.osgienterprise.jpa.eclipselink", OSGI_ENTERPRISE_VERSION),

        // Eclipselink and dependencies
        mavenBundle("org.eclipse.persistence", "org.eclipse.persistence.core", ECLIPSELINK_VERSION),
        mavenBundle("org.eclipse.persistence", "org.eclipse.persistence.jpa", ECLIPSELINK_VERSION),
        mavenBundle("org.eclipse.persistence", "org.eclipse.persistence.asm", ECLIPSELINK_VERSION),
        mavenBundle("org.eclipse.persistence", "org.eclipse.persistence.antlr", ECLIPSELINK_VERSION),
        mavenBundle("org.eclipse.persistence", "javax.persistence", "2.0.2"),

        // Aries JPA and JNDI services and dependencies
        mavenBundle("org.apache.aries", "org.apache.aries.util", ARIES_VERSION),
        mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.api", ARIES_VERSION),
        mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container", ARIES_VERSION),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.api", ARIES_VERSION),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.core", ARIES_VERSION),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.url", ARIES_VERSION),
        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy.api", ARIES_VERSION),
        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy.impl").version("0.3"),
        mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.cglib", "2.1_3_4"),
        // Database 
        mavenBundle("org.apache.derby", "derby", "10.5.3.0_1"),
        
        // Logging
        mavenBundle("org.slf4j", "slf4j-api", "1.5.11"),
        mavenBundle("ch.qos.logback", "logback-core", "0.9.20"),
        mavenBundle("ch.qos.logback", "logback-classic", "0.9.20"),
        
        // OSGi Compendium and Declarative Services         
        mavenBundle("org.osgi", "org.osgi.compendium", "4.2.0"),
        mavenBundle("org.apache.felix", "org.apache.felix.scr", "1.6.0"),

        // Run all tests both on Felix and Equinox
        //felix().version("2.0.1"),
        junitBundles(),
        equinox().version("3.6.0"),
        workingDirectory("/tmp/pax"),
        keepCaches()
        );
        return options;
    }
}
