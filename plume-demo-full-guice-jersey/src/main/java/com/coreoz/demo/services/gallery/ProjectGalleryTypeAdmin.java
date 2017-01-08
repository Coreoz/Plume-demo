package com.coreoz.demo.services.gallery;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.coreoz.demo.db.generated.QCity;
import com.coreoz.demo.webservices.admin.permissions.ProjectAdminPermissions;
import com.coreoz.plume.admin.jersey.WebSessionPermission;
import com.coreoz.plume.file.gallery.webservices.permissions.FileGalleryTypeAdmin;
import com.coreoz.plume.file.gallery.webservices.permissions.FilenamePredicates;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.sql.RelationalPathBase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectGalleryTypeAdmin implements FileGalleryTypeAdmin {

	CITY_GALLERY(QCity.city, QCity.city.id, ProjectAdminPermissions.CITIES_ALTER, null, FilenamePredicates.imagesOnly()),

	;

	private final RelationalPathBase<?> fileGalleryDataEntity;
	private final NumberPath<Long> joinColumn;
	private final String galleryPermission;
	private final BiPredicate<WebSessionPermission, Long> allowedToChangeGallery;
	private final Predicate<String> filenameAllowed;

}
