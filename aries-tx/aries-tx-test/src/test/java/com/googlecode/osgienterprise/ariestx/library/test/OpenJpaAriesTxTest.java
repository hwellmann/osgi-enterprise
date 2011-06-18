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
package com.googlecode.osgienterprise.ariestx.library.test;

import static org.ops4j.pax.exam.CoreOptions.*;

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
public class OpenJpaAriesTxTest extends AbstractLibraryTest
{
    @Configuration
    public static Option[] configuration() {
        Option[] options = options(

        mavenBundle("com.googlecode.osgi-enterprise.jpa", "com.googlecode.osgienterprise.library.datasource").versionAsInProject(),
        mavenBundle("com.googlecode.osgi-enterprise.ariestx", "com.googlecode.osgienterprise.ariestx.library.service").versionAsInProject(),
        mavenBundle("com.googlecode.osgi-enterprise.ariestx", "com.googlecode.osgienterprise.ariestx.library.client").versionAsInProject(),
        mavenBundle("com.googlecode.osgi-enterprise", "com.googlecode.osgienterprise.logback.config").versionAsInProject(),

        // OpenJPA and dependencies
        mavenBundle("org.apache.openjpa", "openjpa").versionAsInProject(),
        mavenBundle("org.apache.geronimo.specs", "geronimo-jpa_2.0_spec").versionAsInProject(),
        mavenBundle("org.apache.geronimo.specs", "geronimo-jta_1.1_spec").versionAsInProject(),
        mavenBundle("commons-lang", "commons-lang").versionAsInProject(),
        mavenBundle("commons-collections", "commons-collections").versionAsInProject(),
        mavenBundle("commons-pool", "commons-pool").versionAsInProject(),
        mavenBundle("commons-dbcp", "commons-dbcp").versionAsInProject(),
        mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.serp").versionAsInProject(),

        // Aries Blueprint
        mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.api").versionAsInProject(),
        mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core").versionAsInProject(),
        mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.manager").versionAsInProject(),
        mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.blueprint.aries").versionAsInProject(),
        mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.blueprint").versionAsInProject(),
        mavenBundle("asm", "asm-all").versionAsInProject(),
        
        // Aries JPA and JNDI services and dependencies
        mavenBundle("org.apache.aries", "org.apache.aries.util").versionAsInProject(),
        mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.api").versionAsInProject(),
        mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container").versionAsInProject(),
        mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container.context").versionAsInProject(),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.api").versionAsInProject(),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.core").versionAsInProject(),
        mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi.url").versionAsInProject(),
        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy.api").versionAsInProject(),
        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy.impl").versionAsInProject(),

        // Database 
        mavenBundle("org.apache.derby", "derby").versionAsInProject(),

        // Logging
        mavenBundle("org.slf4j", "slf4j-api").versionAsInProject(),
        mavenBundle("ch.qos.logback", "logback-core").versionAsInProject(),
        mavenBundle("ch.qos.logback", "logback-classic").versionAsInProject(),

        systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("INFO"),
       
        // JUnit for Pax Exam
        junitBundles(),
        
        equinox(),
        
        /* 
         * Uncomment and edit the following options for diagnosis to see the bundles
         * provisioned by Pax Exam and to restart the framework manually after Pax Exam has finished.  
         */
         workingDirectory("/tmp/pax"),
         keepCaches()
        );
        return options;
    }
}
