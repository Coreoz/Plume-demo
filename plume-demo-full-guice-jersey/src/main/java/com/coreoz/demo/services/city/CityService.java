package com.coreoz.demo.services.city;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.coreoz.demo.db.dao.CityDao;
import com.coreoz.demo.db.generated.City;
import com.coreoz.plume.db.crud.CrudService;
import com.coreoz.plume.file.services.file.FileService;

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

}
