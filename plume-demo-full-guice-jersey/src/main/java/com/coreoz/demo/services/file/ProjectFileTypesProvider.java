package com.coreoz.demo.services.file;

import java.util.Collection;

import com.coreoz.plume.file.gallery.services.file.GalleryFileTypeQuerydsl;
import com.coreoz.plume.file.services.filetype.FileType;
import com.coreoz.plume.file.services.filetype.FileTypesProvider;
import com.google.common.collect.ImmutableList;

public class ProjectFileTypesProvider implements FileTypesProvider {

	@Override
	public Collection<FileType> fileTypesAvailable() {
		return ImmutableList
			.<FileType> builder()
			.add(ProjectFileType.values())
			.add(GalleryFileTypeQuerydsl.PLUME_GALLERY)
			.build();
	}

}
