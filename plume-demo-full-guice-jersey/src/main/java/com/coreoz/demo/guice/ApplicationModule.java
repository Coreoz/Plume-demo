package com.coreoz.demo.guice;

import org.glassfish.jersey.server.ResourceConfig;

import com.coreoz.demo.db.InitializeDatabase;
import com.coreoz.demo.jersey.JerseyConfigProvider;
import com.coreoz.demo.services.file.ProjectFileTypesProvider;
import com.coreoz.demo.services.gallery.ProjectFileGalleryTypesAdminProvider;
import com.coreoz.demo.webservices.admin.permissions.ProjectAdminPermissionService;
import com.coreoz.plume.admin.guice.GuiceAdminWsModule;
import com.coreoz.plume.admin.jersey.feature.WebSessionClassProvider;
import com.coreoz.plume.admin.services.permissions.AdminPermissionService;
import com.coreoz.plume.admin.webservices.security.WebSessionAdminProvider;
import com.coreoz.plume.admin.webservices.security.WebSessionProvider;
import com.coreoz.plume.conf.guice.GuiceConfModule;
import com.coreoz.plume.db.guice.DataSourceModule;
import com.coreoz.plume.db.querydsl.guice.GuiceQuerydslModule;
import com.coreoz.plume.file.gallery.guice.GuiceFileGalleryModuleQuerydsl;
import com.coreoz.plume.file.gallery.services.gallerytype.FileGalleryTypesProvider;
import com.coreoz.plume.file.gallery.webservices.permissions.FileGalleryTypesAdminProvider;
import com.coreoz.plume.file.guice.GuiceFileModuleQuerydsl;
import com.coreoz.plume.file.services.filetype.FileTypesProvider;
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
		install(new GuiceAdminWsModule());
		bind(WebSessionProvider.class).to(WebSessionAdminProvider.class);
		bind(WebSessionClassProvider.class).to(WebSessionAdminProvider.class);
		bind(AdminPermissionService.class).to(ProjectAdminPermissionService.class);

		// database setup for the demo
		install(new DataSourceModule());
		bind(InitializeDatabase.class).asEagerSingleton();

		// file management
		install(new GuiceFileModuleQuerydsl());
		bind(FileTypesProvider.class).to(ProjectFileTypesProvider.class);
		// media gallery management
		install(new GuiceFileGalleryModuleQuerydsl());
		bind(FileGalleryTypesProvider.class).to(ProjectFileGalleryTypesAdminProvider.class);
		bind(FileGalleryTypesAdminProvider.class).to(ProjectFileGalleryTypesAdminProvider.class);

		// prepare Jersey configuration
		bind(ResourceConfig.class).toProvider(JerseyConfigProvider.class);
	}

}
