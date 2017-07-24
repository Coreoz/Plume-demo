package com.coreoz.demo.webservices.admin;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.coreoz.demo.services.city.CityService;
import com.coreoz.demo.services.city.CityWithImage;
import com.coreoz.demo.services.city.CityWithImageUpload;
import com.coreoz.demo.webservices.admin.permissions.ProjectAdminPermissions;
import com.coreoz.plume.admin.jersey.feature.RestrictToAdmin;
import com.coreoz.plume.jersey.errors.Validators;

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

	@POST
	@ApiOperation("Create or save a city")
	@RestrictToAdmin(ProjectAdminPermissions.CITIES_ALTER)
	public CityWithImage save(CityWithImageUpload cityToSave) {
		// If a validator fails, an exception is raised so the next instructions won't be called
		Validators.checkRequired("City data", cityToSave.getData());
		Validators.checkRequired("City name", cityToSave.getData().getName());
		if(cityToSave.getData().getId() == null) {
			Validators.checkRequired("City image", cityToSave.getCityImage());
		}
		// Note that we are not validating the City image type: that is insecure.
		// Here it is acceptable because we do trust our admin users with the CITIES_ALTER permission.
		// A malicious user can:
		// - upload a file containing a virus and spread it using our server,
		// - upload a very big picture that will weight on our database.
		// If you don't trust your users, you should always resize the images uploaded ; this way:
		// - if the file sent is not an image, an error will be raised,
		// - if the image is to large, the thumbnail saved in the database will be lighter.
		// A good library to resize images is Thumbnailator.

		// by default a city is inactive
		if(cityToSave.getData().getActive() == null) {
			cityToSave.getData().setActive(false);
		}

		return cityService.save(cityToSave);
	}

	@DELETE
	@Path("/{cityId}")
	@ApiOperation("Delete a city")
	@RestrictToAdmin(ProjectAdminPermissions.CITIES_ALTER)
	public void delete(@PathParam("cityId") Long cityId) {
		cityService.delete(cityId);
	}

}
