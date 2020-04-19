package com.coreoz.demo.webservices.internal;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coreoz.demo.services.configuration.ConfigurationService;
import com.coreoz.plume.admin.services.permissions.AdminPermissions;
import com.coreoz.plume.admin.webservices.SessionWs;
import com.coreoz.plume.admin.websession.WebSessionPermission;
import com.coreoz.plume.jersey.security.basic.BasicAuthenticator;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableSet;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.util.Json;

/**
 * To use in Swagger UI:<br>
 * 1/ First go to http://localhost:8080/webjars/swagger-ui/2.1.5/index.html?url=/api/swaggerAdmin<br>
 * 2/ Log in with the default credentials: plume // rocks<br>
 * 3/ Log in the session WS: admin // admin<br>
 * 4/ copy the JWT token<br>
 * 5/ click the "Authorize" button<br>
 * 6/ Type: Bearer [the JWT token you copied]<br>
 * 	=> for example: "Bearer plkok.sqdqsdsqd.cqsdsd"
 */
@Path("/swaggerAdmin")
@PublicApi
@Singleton
public class SwaggerAdminWs {

	private final String swaggerDefinition;
	private final BasicAuthenticator<String> basicAuthenticator;

	@Inject
	public SwaggerAdminWs(ConfigurationService configurationService) {
		BeanConfig beanConfig = new BeanConfig() {
			@Override
			public Set<Class<?>> classes() {
				return ImmutableSet
					.<Class<?>> builder()
					.add(SessionWs.class)
					.addAll(super.classes())
					.build();
			}
		};
		beanConfig.setResourcePackage("com.coreoz.demo.webservices.admin");
		beanConfig.setBasePath("/api");
		beanConfig.setTitle("Plume Demo Admin API");
		// this is not only a setter, it also starts the Swagger classes analyzing process
		beanConfig.setScan(true);

		// the swagger object can be changed to add security definition
		// or to alter the generated mapping
		Swagger swagger = beanConfig.getSwagger();
		swagger.addSecurityDefinition(
			"Bearer",
			new ApiKeyAuthDefinition("Authorization", In.HEADER)
		);

		// serialization of the Swagger definition
		try {
			this.swaggerDefinition = Json.mapper().writeValueAsString(swagger);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// require authentication to access the API documentation
		this.basicAuthenticator = BasicAuthenticator.fromSingleCredentials(
			configurationService.swaggerAccessUsername(),
			configurationService.swaggerAccessPassword(),
			"Plume demo admin API"
		);
	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public String get(@Context ContainerRequestContext requestContext,
			@Context WebSessionPermission adminUser) throws JsonProcessingException {
		// when swagger UI will use the JWT authorization header,
		// it will reload the definition with the basic header replaced by the JWT header
		// => so in this case, we check that the JWT header match an admin user, ie with the MANAGE_USERS role
		if(adminUser == null || !adminUser.getPermissions().contains(AdminPermissions.MANAGE_USERS)) {
			basicAuthenticator.requireAuthentication(requestContext);
		}

		return swaggerDefinition;
	}

}

