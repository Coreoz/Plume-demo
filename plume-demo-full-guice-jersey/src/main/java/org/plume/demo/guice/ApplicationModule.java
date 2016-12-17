package org.plume.demo.guice;

import org.glassfish.jersey.server.ResourceConfig;
import org.plume.demo.db.InitializeDatabase;
import org.plume.demo.jersey.JerseyConfigProvider;

import com.coreoz.plume.admin.guice.GuiceAdminWithDefaultsModule;
import com.coreoz.plume.conf.guice.GuiceConfModule;
import com.coreoz.plume.db.guice.DataSourceModule;
import com.coreoz.plume.db.querydsl.guice.GuiceQuerydslModule;
import com.coreoz.plume.jersey.guice.GuiceJacksonModule;
import com.google.inject.AbstractModule;

/**
 * Group the Guice modules to install in the application
 */
public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new GuiceConfModule());
		install(new GuiceJacksonModule());
		install(new GuiceQuerydslModule());
		// admin module
		install(new GuiceAdminWithDefaultsModule());

		// database setup for the demo
		install(new DataSourceModule());
		bind(InitializeDatabase.class).asEagerSingleton();

		// prepare Jersey configuration
		bind(ResourceConfig.class).toProvider(JerseyConfigProvider.class);
	}

}
