package org.plume.demo.jersey;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.plume.demo.WebApplication;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.coreoz.plume.jersey.grizzly.GrizzlyErrorPageHandler;

/**
 * Configure and start a Grizzly server
 */
public class GrizzlySetup {

	public static void start(ResourceConfig jerseyResourceConfig) throws IOException {
		// replace JUL logger (used by Grizzly) by SLF4J logger
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		// create the server
		URI baseUri = UriBuilder.fromUri("http://localhost/api").port(8080).build();
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
			baseUri,
			jerseyResourceConfig,
			// the server have to be started after the configuration is complete,
			// else the custom error page generator won't be used
			false
		);

		// minimal error page to avoid leaking server information
		httpServer.getServerConfiguration().setDefaultErrorPageGenerator(new GrizzlyErrorPageHandler());

		// webjars for swagger ui
		HttpHandler webJarHandler = new CLStaticHttpHandler(
			Thread.currentThread().getContextClassLoader(),
			"META-INF/resources/webjars/"
		);
		httpServer.getServerConfiguration().addHttpHandler(webJarHandler, "/webjars/");

		// static resources
		HttpHandler httpHandler = new CLStaticHttpHandler(
			WebApplication.class.getClassLoader(),
			"/statics/"
		);
		httpServer.getServerConfiguration().addHttpHandler(httpHandler);

		httpServer.start();
	}

}
