## Motivation ##

After some first experiments with Aries components on the one hand and with OSGified Wicket on the other hand, I felt a need for a sample application slightly less trivial than a simple Hello World page.

The Aries samples use plain old servlets and JSPs, which might lead people to believe that cool new modular OSGi enterprise applications only can work with fairly dated web technology.

So I took the Aries Blog sample and rewrote the web bundle using Wicket and [wicketstuff-osgi](https://github.com/wicketstuff/core/wiki/Osgi).

The [aries-blog](http://code.google.com/p/osgi-enterprise/source/browse/aries-blog) subproject of this site contains two web modules, the original one from Aries using servlets and a new one using Wicket.

In addition, I converted the Maven projects to use Tycho, to be able to use Eclipse PDE to launch the sample directly from my Eclipse workspace with an OSGi Framework launcher.

## Building the sample ##

Since this sample is currently based on a number of bleeding edge snapshot versions of Wicketstuff and Tycho, the build is not as simple as it should be.


  * clone the repository, e.g. to `~/work/osgi-enterprise.`
  * Make sure you use Maven 3.0.x
  * `cd ~/work/osgi-enterprise/aries-p2`
  * `mvn -P wicketstuff-snapshot clean`
  * This was a workaround to download the wicketstuff artifacts to your local maven repository. Tycho platform builds seem to have a problem resolving artifacts from remote snapshot repositories, so the snapshot is not enabled during the normal build.
  * `mvn install`
  * This will build a p2 Repository, aka Eclipse Target Platform
  * `cd com.googlecode.osgienterprise.ariespde.platform.site/target`
  * Unpack the p2 archive to a location of your choice, e.g. `/usr/local/targets/aries-p2`. This location will be used as target platform both for your Eclipse workspace and for the Tycho command line builds.
  * `export aries_p2=file:/usr/local/targets/aries-p2`
  * `cd ~/work/osgi-enterprise/aries-p2/blog`
  * `mvn install`
  * `cd blog-wicket-run`
  * `mvn pax:provision`
  * This will run the sample application on Equinox.
  * Open the Blog sample start page `http://localhost:8080/blog` in your browser.
  * Type `close` at the `osgi>` prompt to shut down the application.
