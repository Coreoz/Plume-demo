package com.coreoz.demo.services.file;

import com.coreoz.demo.db.generated.QCity;
import com.coreoz.plume.file.services.filetype.FileType;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.sql.RelationalPathBase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectFileType implements FileType {

	CITY_IMAGE(QCity.city, QCity.city.idFileImage)

	;
	private final RelationalPathBase<?> fileEntity;
	private final NumberPath<Long> joinColumn;

}
