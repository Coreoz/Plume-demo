package com.coreoz.demo.services.gallery;

import java.util.Collection;

import com.coreoz.plume.file.gallery.webservices.permissions.FileGalleryTypeAdmin;
import com.coreoz.plume.file.gallery.webservices.permissions.FileGalleryTypesAdminProvider;

import jersey.repackaged.com.google.common.collect.ImmutableList;

public class ProjectFileGalleryTypesAdminProvider implements FileGalleryTypesAdminProvider {

	@Override
	public Collection<FileGalleryTypeAdmin> fileGalleryTypesAdminAvailable() {
		return ImmutableList.copyOf(ProjectGalleryTypeAdmin.values());
	}

}

