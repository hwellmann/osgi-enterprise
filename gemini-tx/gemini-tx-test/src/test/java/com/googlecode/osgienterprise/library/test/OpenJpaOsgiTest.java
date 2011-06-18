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

import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.keepCaches;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.workingDirectory;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;


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
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class OpenJpaOsgiTest extends AbstractLibraryTest
{
    @Configuration
    public static Option[] configuration() {
        Option[] options = options(

        mavenBundle("com.googlecode.osgi-enterprise.jpa", "com.googlecode.osgienterprise.library.datasource").versionAsInProject(),
        mavenBundle("com.googlecode.osgi-enterprise.geminitx", "com.googlecode.osgienterprise.geminitx.library.service").versionAsInProject(),
        mavenBundle("com.googlecode.osgi-enterprise.geminitx", "com.googlecode.osgienterprise.geminitx.library.client").versionAsInProject(),
        mavenBundle("com.googlecode.osgi-enterprise", "com.googlecode.osgienterprise.logback.config").versionAsInProject(),

        // OpenJPA and dependencies
        mavenBundle("org.apache.openjpa", "openjpa").versionAsInProject(),
        mavenBundle("org.apache.geronimo.specs", "geronimo-jpa_2.0_spec").versionAsInProject(),
        mavenBundle("org.apache.geronimo.specs", "geronimo-jta_1.1_spec").versionAsInProject(),
        mavenBundle("commons-lang", "commons-lang").versionAsInProject(),
        mavenBundle("commons-collections", "commons-collections").versionAsInProject(),
        mavenBundle("commons-pool", "commons-pool").versionAsInProject(),
        mavenBundle("commons-dbcp", "commons-dbcp").versionAsInProject(),
        mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.serp"),

        // Aries JPA and JNDI services and dependencies
        mavenBundle("org.apache.aries", "org.apache.aries.util").versionAsInProject(),
        mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.api").versionAsInProject(),
        mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container").versionAsInProject(),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.api").versionAsInProject(),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.core").versionAsInProject(),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.url").versionAsInProject(),
        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy.api").versionAsInProject(),
        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy.impl").versionAsInProject(),
        mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.cglib").versionAsInProject(),
        // Database 
        mavenBundle("org.apache.derby", "derby", "10.8.1.2"),
        
        // Gemini Blueprint
        mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-io").versionAsInProject(),
        mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-core").versionAsInProject(),
        mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-extender").versionAsInProject(),
        mavenBundle("org.springframework", "spring-aop").versionAsInProject(),
        mavenBundle("org.springframework", "spring-asm").versionAsInProject(),
        mavenBundle("org.springframework", "spring-beans").versionAsInProject(),
        mavenBundle("org.springframework", "spring-context").versionAsInProject(),
        mavenBundle("org.springframework", "spring-core").versionAsInProject(),
        mavenBundle("org.springframework", "spring-expression").versionAsInProject(),
        mavenBundle("org.springframework", "spring-jdbc").versionAsInProject(),
        mavenBundle("org.springframework", "spring-orm").versionAsInProject(),
        mavenBundle("org.springframework", "spring-tx").versionAsInProject(),
        mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.aopalliance").versionAsInProject(),


        // Logging
        mavenBundle("org.slf4j", "slf4j-api", "1.6.1"),
        mavenBundle("ch.qos.logback", "logback-core", "0.9.29"),
        mavenBundle("ch.qos.logback", "logback-classic", "0.9.29"),
        
        // OSGi Compendium and Declarative Services         
        //mavenBundle("org.osgi", "org.osgi.compendium", "4.2.0"),
        //mavenBundle("org.apache.felix", "org.apache.felix.scr", "1.6.0"),

        
        
        // Run all tests both on Felix and Equinox
        //felix().version("2.0.1"),
        junitBundles(),
        //equinox().version("3.6.0"),
        felix(),
        workingDirectory("/tmp/pax")
        //keepCaches()
        );
        return options;
    }
}
