package com.coreoz.demo.webservices.api;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.coreoz.demo.services.configuration.ConfigurationService;
import com.coreoz.demo.webservices.api.data.Test;
import com.coreoz.plume.jersey.security.permission.PublicApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/example")
@Api("Manage exemple web-services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class ExampleWs {

	private final ConfigurationService configurationService;

	@Inject
	public ExampleWs(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@GET
	@Path("/test/{name}")
	@ApiOperation("Example web-service")
	public Test test(@ApiParam(required = true) @PathParam("name") String name) {
		return new Test("hello " + name + "\n" + configurationService.hello());
	}

}
