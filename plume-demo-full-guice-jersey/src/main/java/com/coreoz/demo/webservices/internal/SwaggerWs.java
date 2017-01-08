package com.coreoz.demo.webservices.internal;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coreoz.demo.services.configuration.ConfigurationService;
import com.coreoz.plume.jersey.security.basic.BasicAuthenticator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Throwables;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import io.swagger.util.Json;

@Path("/swagger")
@Singleton
public class SwaggerWs {

	private final String swaggerDefinition;
	private final BasicAuthenticator<String> basicAuthenticator;

	@Inject
	public SwaggerWs(ConfigurationService configurationService) {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setResourcePackage("org.plume.demo.webservices.api");
		beanConfig.setBasePath("/api");
		beanConfig.setTitle("API plume-demo-full-guice-jersey");
		// this is not only a setter, it also starts the Swagger classes analyzing process
		beanConfig.setScan(true);

		// the swagger object can be changed to add security definition
		// or to alter the generated mapping
		Swagger swagger = beanConfig.getSwagger();

		// serialization of the Swagger definition
		try {
			this.swaggerDefinition = Json.mapper().writeValueAsString(swagger);
		} catch (JsonProcessingException e) {
			throw Throwables.propagate(e);
		}

		// require authentication to access the API documentation
		this.basicAuthenticator = BasicAuthenticator.fromSingleCredentials(
			configurationService.swaggerAccessUsername(),
			configurationService.swaggerAccessPassword(),
			"Plume demo API"
		);
	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public String get(@Context ContainerRequestContext requestContext) throws JsonProcessingException {
		basicAuthenticator.requireAuthentication(requestContext);

		return swaggerDefinition;
	}

}

