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
package com.googlecode.osgienterprise.library.datasource;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registers an in-memory Derby data source in the OSGi service registry.
 * 
 * @author Harald Wellmann
 *
 */
public class DerbyDataSourceProvider implements BundleActivator {
    private Logger log = LoggerFactory.getLogger(DerbyDataSourceProvider.class);
    private DataSource ds;

    public void start(BundleContext context) throws Exception {
        log.info("creating data source");
        ds = createDataSource();
        context.registerService(DataSource.class.getName(), ds, null);
    }

    public void stop(BundleContext context) throws Exception {
    }

    private DataSource createDataSource() {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:itest");
        ds.setCreateDatabase("create");
        return ds;
    }
}
