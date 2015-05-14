## Issues ##

  * Wicket JARs do come as OSGi bundles, but they use `DynamicImport-Package: *`, which defeats the purpose of modularity and should not be used by a good OSGi citizen.

  * On servlet context initialization, Wicket needs to load the `WebApplication` class from each WAB.

  * When deserializing pages from its page store, Wicket needs to load client classes, not only from WABs, but also from other bundles imported by WABs.

  * There should be a standard way to inject OSGi services into Wicket components (similiar to using `@SpringBean` annotations from wicket-spring to inject Spring beans).

## Solutions ##

### Manifests ###

I rewrapped `wicket` and `wicket-ioc` 1.4.17 with the maven-bundle-plugin, creating new manifests that do not use dynamic imports. This is the only modification to official Wicket artifacts.

### Glue code ###

All required glue code is in a separate fragment bundle `com.googlecode.osgienterprise.wicket.osgi` extending the core wicket bundle. The fragment is just a temporary solution, see below for more details.

### Loading the `WebApplication` class ###

The default way of specifying the class name of the `WebApplication` class as a `WicketFilter` parameter in `web.xml` does not work in OSGi, as Wicket would have to load a class by name from the WAB, which is not visible to Wicket's own classloader.

Instead, there is an `OsgiWebApplicationFactory` which looks up a `WebApplication` service from the OSGi service registry with the property `wicket.osgi.application.name` matching a given value.

```
  <filter>
    <filter-name>Wicket</filter-name>
    <filter-class>org.apache.wicket.protocol.http.WicketFilter</filter-class>
    <init-param>
      <param-name>applicationFactoryClassName</param-name>
      <param-value>com.googlecode.osgienterprise.wicket.OsgiWebApplicationFactory</param-value>
    </init-param>
    <init-param>
      <param-name>wicket.osgi.application.name</param-name>
      <param-value>library</param-value>
    </init-param>
  </filter>
```

### Deserialization ###

To make deserialization work, an `OsgiClassResolver` is installed in the application settings. Example:

```
public class LibraryApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        getApplicationSettings().setClassResolver(new OsgiClassResolver());
    }
}
```

This resolver uses the WAB's bundle context accessed via the servlet context to resolve any classes that cannot directly be loaded by the Wicket bundle classloader.

Thus, the resolver can see all classes visible to Wicket **and** to the WAB.

### Injection ###

There is an `OsgiComponentInjector` (modelled on the `SpringComponentInjector` from wicket-spring) which needs to be registered in the `WebApplication` class to inject OSGi services into Wicket components on instantiation:

```
public class LibraryApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        addComponentInstantiationListener(new OsgiComponentInjector());    // <-- enables @Inject
        getApplicationSettings().setClassResolver(new OsgiClassResolver());
    }
}
```

The injector scans the component class for fields annotated by `@javax.inject.Inject`, looks up a service of the required type for the given field from the registry, and injects this service wrapped in a proxy.

The proxy takes care of serialization, it simply replaces the service by its interface name and looks its up from the service registry again on deserialization.

**Note:** This is just the first shot implementation, which works fine as long as there's only one service matching the given injection point. In the next step, the `OsgiInjector` shall be aware of qualifier annotations expressing further constraints on the service in terms of service properties.

## Building and running the sample ##

The sample project for this solution is an extension of the aries-tx sample. The application bundles are Eclipse PDE projects, the target platform is built with Maven.

### Prerequisites: ###

  * Eclipse 3.6.2
  * Maven 3.0.3
  * Mercurial 1.7.5

### Building the target platform ###

  * Clone the osgi-enterprise Mercurial repository.
  * Update your working copy to tag 0.1.0.
  * From the root of your working copy, run `mvn -DskipTests=true install`.
  * You will find the target platform in `aries-p2/target/aries-p2-0.0.1-SNAPSHOT-platform.zip`.
  * Unpack the platform archive to a folder of your choice and set this folder as the active target platform in your Eclipse workspace.

### Building and running the application ###

  * `aries-pde` has 5 subfolders with Eclipse Plugin projects. Import these projects into your Eclipse workspace.
  * The JPA entity classes need to be bytecode-enhanced for the OpenJPA peristence provider. Run the Ant script `com.googlecode.osgienterprise.ariespde.library.service/enhance.xml` to enhance your classes. This requires setting two Ant properties `platform.install` and `target.folder`. See the header comment in the Ant script for more details.
  * Run the launcher `com.googlecode.osgienterprise.ariespde.library.web/aries-pde.launch`, or create your own OSGi Framework launcher including the 5 workspace bundles and the entire target platform.
  * In the OSGi console, you should now see the startup messages from Aries Blueprint, Jetty and Wicket.
  * Open the Library sample home page in your web browser at `http://localhost:8080/library`.

## Points to note ##

This sample still works even when not installing the `OsgiClassResolver` in `Application.init()`. This is because either Jetty itself or the Pax Web extender bundle sets the thread context classloder to the WAB classloader.

For the same reason, the `OsgiWebApplicationFactory` can be omitted, Wicket can still load the application class by name via the `ThreadContextClassLoader`.

However, this appears to be an implementation detail of Jetty or Pax Web. The OSGi Web Applications Specification does not mention any classloaders, so I don't think it is safe to rely on this behaviour.

It would be interesting to try this sample on other OSGi compliant web containers.

The fact that `com.googlecode.osgienterprise.wicket.osgi` is a fragment and that WABs need to depend on it is rather ugly. This fragment started its life as an ordinary bundle, but then I realized this only worked by coincidence due to the `ThreadContextClassLoader` magic, because Wicket does not depend on my bundle, and this dependency would create a cycle anyway.

A cleaner solution would require a change in Wicket core:
  * Option A: Wicket offers an API `setWebApplicationFactory()` to override the default factory. `com.googlecode.osgienterprise.wicket.osgi` uses this API to register its `OsgiWebApplicationFactory` on activation.
  * Option B: Wicket looks up the `IWebApplicationFactory` from the OSGi service registry. This can be done by reflection to avoid run-time dependencies on OSGi for non-OSGi Wicket users. (Example: OSGi support in OpenJPA.)