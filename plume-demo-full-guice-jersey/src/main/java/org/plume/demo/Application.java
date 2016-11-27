package org.plume.demo;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.ErrorPageGenerator;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.plume.demo.jee.JerseyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws IOException {
		long startTimestamp = System.currentTimeMillis();

		// replace JUL logger by slf4j
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		// create the server
		URI baseUri = UriBuilder.fromUri("http://localhost/api").port(8080).build();
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
			baseUri,
			new JerseyConfig(),
			// the server have to be started after the configuration is complete,
			// else at least the custom error page generator won't be used
			false
		);

		// minimal error page to avoid leaking server information
		httpServer.getServerConfiguration().setDefaultErrorPageGenerator(new ErrorPageHandler());

		// webjars
		HttpHandler webJarHandler = new CLStaticHttpHandler(
			Thread.currentThread().getContextClassLoader(),
			"META-INF/resources/webjars/"
		);
		httpServer.getServerConfiguration().addHttpHandler(webJarHandler, "/webjars/");

		// static resources
		HttpHandler httpHandler = new CLStaticHttpHandler(
			Application.class.getClassLoader(),
			"/statics/"
		);
		httpServer.getServerConfiguration().addHttpHandler(httpHandler);

		httpServer.start();

		logger.info("Server started in {} ms", System.currentTimeMillis() - startTimestamp);
	}

	private static class ErrorPageHandler implements ErrorPageGenerator {
		@Override
		public String generate(Request request, int status, String reasonPhrase, String description,
				Throwable exception) {
			if(exception != null) {
				logger.error(
					"Error raised on {}?{}",
					request.getPathInfo(),
					request.getQueryString(),
					exception
				);
			}

			return status + " " + reasonPhrase;
		}

	}

}
