package org.plume.demo.jee;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.ServiceLocatorProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.plume.demo.guice.Factory;

import com.coreoz.plume.jersey.errors.WsResultExceptionMapper;
import com.coreoz.plume.jersey.java8.TimeParamProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * Jersey configuration
 */
public class JerseyConfig extends ResourceConfig {

	@Inject
	public JerseyConfig() {
		packages("org.plume.demo.webservices");

		// filters configuration
		// handle errors and exceptions
		register(WsResultExceptionMapper.class);
		// to debug web-service requests
		// register(LoggingFilter.class);

		// java 8
		register(TimeParamProvider.class);

		// WADL is like swagger for jersey
		// by default it should be disabled to prevent leaking API documentation
		property("jersey.config.server.wadl.disableWadl", true);

		// jackson mapper configuration
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(Factory.injector().getInstance(ObjectMapper.class));
		register(provider);

		register(GuiceFeature.class);
	}

	private static class GuiceFeature implements Feature {
		@Override
		public boolean configure(FeatureContext context) {
			ServiceLocator serviceLocator = ServiceLocatorProvider.getServiceLocator(context);

			// Guice configuration to use it instead of HK2 to create instances of web-services
			GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
			GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
			guiceBridge.bridgeGuiceInjector(Factory.injector());

			return true;
		}
	}

}