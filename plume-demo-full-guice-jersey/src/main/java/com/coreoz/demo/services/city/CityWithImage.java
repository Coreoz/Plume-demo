package com.coreoz.demo.services.city;

import com.coreoz.demo.db.generated.City;

import lombok.Value;

@Value(staticConstructor = "of")
public class CityWithImage {

	private final City data;
	private final String cityImageUrl;

}
