package com.coreoz.demo.services.configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.typesafe.config.Config;

@Singleton
public class ConfigurationService {

	private final Config config;

	@Inject
	public ConfigurationService(Config config) {
		this.config = config;
	}

	public String hello() {
		return config.getString("hello");
	}

	public String swaggerAccessUsername() {
		return config.getString("swagger.access.username");
	}

	public String swaggerAccessPassword() {
		return config.getString("swagger.access.password");
	}

}

