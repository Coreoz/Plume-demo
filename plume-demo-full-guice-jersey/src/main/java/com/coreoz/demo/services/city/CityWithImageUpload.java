package com.coreoz.demo.services.city;

import com.coreoz.demo.db.generated.City;
import com.coreoz.plume.file.services.file.data.FileUploadBase64;

import lombok.Value;

@Value(staticConstructor = "of")
public class CityWithImageUpload {

	private final City data;
	private final FileUploadBase64 cityImage;

}
