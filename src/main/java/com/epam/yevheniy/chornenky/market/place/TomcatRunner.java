package com.epam.yevheniy.chornenky.market.place;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import java.io.File;

public class TomcatRunner {
    private static final int WEB_PORT = 80;

    public static void main(final String[] args) throws LifecycleException {
        final String webappDirLocation = "src/main/webapp/";
        final Tomcat tomcat = new Tomcat();

        tomcat.setPort(Integer.valueOf(WEB_PORT));

        tomcat.getConnector();

        final StandardContext ctx = (StandardContext) tomcat.addWebapp(
                "/", new File(webappDirLocation).getAbsolutePath()
        );

        ctx.setAllowCasualMultipartParsing(true);

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        final File additionWebInfClasses = new File("target/classes");
        final WebResourceRoot resources = new StandardRoot(ctx);

        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
