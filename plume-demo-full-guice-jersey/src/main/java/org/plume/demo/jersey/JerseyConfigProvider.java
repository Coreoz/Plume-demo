package org.plume.demo.jersey;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.server.ResourceConfig;

import com.coreoz.plume.admin.webservices.security.AdminSecurityFeature;
import com.coreoz.plume.jersey.errors.WsResultExceptionMapper;
import com.coreoz.plume.jersey.java8.TimeParamProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * Jersey configuration
 */
@Singleton
public class JerseyConfigProvider implements Provider<ResourceConfig> {

	private final ResourceConfig config;

	@Inject
	public JerseyConfigProvider(ObjectMapper objectMapper) {
		config = new ResourceConfig();

		// to run the admin ui app separately, if included the java project,
		// these lines can be deleted
		config.register(new ContainerResponseFilter() {
			@Override
			public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
					throws IOException {
				responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
				responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
				responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
				responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			}
		});

		// this package will be scanned by Jersey to discover web-service classes
		config.packages("org.plume.demo.webservices");
		// admin web-services
		config.packages("com.coreoz.plume.admin.webservices");

		// filters configuration
		// handle errors and exceptions
		config.register(WsResultExceptionMapper.class);
		// admin web-services protection with the permission system
		config.register(AdminSecurityFeature.class);
		// to debug web-service requests
		// register(LoggingFilter.class);

		// java 8
		config.register(TimeParamProvider.class);

		// WADL is like swagger for jersey
		// by default it should be disabled to prevent leaking API documentation
		config.property("jersey.config.server.wadl.disableWadl", true);

		// jackson mapper configuration
		JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
		jacksonProvider.setMapper(objectMapper);
		config.register(jacksonProvider);
	}

	@Override
	public ResourceConfig get() {
		return config;
	}

}