package com.coreoz.demo.webservices.admin;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.coreoz.demo.services.city.CityService;
import com.coreoz.demo.services.city.CityWithImage;
import com.coreoz.demo.webservices.admin.permissions.ProjectAdminPermissions;
import com.coreoz.plume.admin.webservices.security.RestrictToAdmin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Path("/admin/city")
@Api(value = "Manage cities", authorizations = @Authorization("Bearer"))
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RestrictToAdmin(ProjectAdminPermissions.CITIES_ACCESS)
@Singleton
public class CityWs {

	private final CityService cityService;

	@Inject
	public CityWs(CityService cityService) {
		this.cityService = cityService;
	}

	@GET
	@ApiOperation("Fetch all cities")
	public List<CityWithImage> all() {
		return cityService.findAllWithImage();
	}

}
