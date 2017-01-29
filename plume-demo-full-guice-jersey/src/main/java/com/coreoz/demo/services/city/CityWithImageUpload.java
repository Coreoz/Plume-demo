package com.coreoz.demo.services.city;

import com.coreoz.demo.db.generated.City;
import com.coreoz.plume.file.services.file.data.FileUploadBase64;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityWithImageUpload {

	private City data;
	private FileUploadBase64 cityImage;

}
