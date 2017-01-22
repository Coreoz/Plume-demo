package com.coreoz.demo.services.city;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.coreoz.demo.db.dao.CityDao;
import com.coreoz.demo.db.generated.City;
import com.coreoz.demo.services.file.ProjectFileType;
import com.coreoz.plume.db.crud.CrudService;
import com.coreoz.plume.file.services.file.FileService;
import com.coreoz.plume.file.services.file.data.FileUploaded;

@Singleton
public class CityService extends CrudService<City> {

	private final CityDao cityDao;
	private final FileService fileService;

	@Inject
	public CityService(CityDao cityDao, FileService fileService) {
		super(cityDao);

		this.cityDao = cityDao;
		this.fileService = fileService;
	}

	public List<CityWithImage> findAllWithImage() {
		return cityDao
			.findAll()
			.stream()
			.map(city -> CityWithImage.of(
				city,
				fileService.url(city.getIdFileImage()).orElse(null)
			))
			.collect(Collectors.toList());
	}

	public void save(CityWithImageUpload cityToSave) {
		// Here we are doing something insecure, but that's ok because
		// we do trust our admin users with the CITIES_ALTER permission.
		// A malicious user could change the ID of the picture to reference an other file
		// in the database ; this other file may not be a picture.
		cityToSave.getData().setIdFileImage(
			fileService
				.upload(ProjectFileType.CITY_IMAGE, cityToSave.getCityImage())
				.map(FileUploaded::getId)
				// If you want your code to be completely safe, replace this statement by:
				// .orElse(cityDao.currentFileImageId(cityToSave.getData().getId()))
				// you will then need to implement currentFileImageId() in the DAO
				.orElse(cityToSave.getData().getIdFileImage())
		);

		cityDao.save(cityToSave.getData());
	}

}
