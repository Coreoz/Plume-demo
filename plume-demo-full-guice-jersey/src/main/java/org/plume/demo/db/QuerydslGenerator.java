package org.plume.demo.db;

import java.io.File;
import java.sql.SQLException;
import java.util.Locale;

import com.coreoz.plume.conf.guice.GuiceConfModule;
import com.coreoz.plume.db.querydsl.generation.IdBeanSerializer;
import com.coreoz.plume.db.transaction.TransactionManager;
import com.google.common.base.Throwables;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.querydsl.codegen.EntityType;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.codegen.DefaultNamingStrategy;
import com.querydsl.sql.codegen.MetaDataExporter;
import com.querydsl.sql.types.JSR310LocalDateTimeType;
import com.querydsl.sql.types.JSR310LocalDateType;
import com.querydsl.sql.types.JSR310LocalTimeType;
import com.querydsl.sql.types.JSR310ZonedDateTimeType;
import com.querydsl.sql.types.Type;

public class QuerydslGenerator {

	private static final String TABLES_PREFIX = "plm_";

	public static void main(String... args) {
		Configuration configuration = new Configuration(SQLTemplates.DEFAULT);
		configuration.register(classType(JSR310LocalDateTimeType.class));
		configuration.register(classType(JSR310LocalDateType.class));
		configuration.register(classType(JSR310LocalTimeType.class));
		configuration.register(classType(JSR310ZonedDateTimeType.class));
		configuration.registerNumeric(1, 0, Boolean.class);
		configuration.registerNumeric(9, 0, Long.class);
		configuration.registerNumeric(19, 0, Long.class);

		MetaDataExporter exporter = new MetaDataExporter();
		exporter.setPackageName("org.plume.demo.db.generated");
		exporter.setTargetFolder(new File("src/main/java"));
		exporter.setTableNamePattern(TABLES_PREFIX + "%");
		exporter.setNamingStrategy(new DefaultNamingStrategy() {
			@Override
			public String getClassName(String tableName) {
				// uncomment if you are using plume file
//				if("plm_file".equalsIgnoreCase(tableName)) {
//					return FileEntityQuerydsl.class.getName();
//				}
				return super.getClassName(tableName.substring(TABLES_PREFIX.length()));
			}

			@Override
			public String getDefaultVariableName(EntityType entityType) {
				String variableName = getClassName(entityType.getData().get("table").toString());
				return variableName.substring(0, 1).toLowerCase(Locale.ENGLISH) + variableName.substring(1);
			}
		});
		exporter.setBeanSerializer(new IdBeanSerializer().setUseJacksonAnnotation(true));
		exporter.setColumnAnnotations(true);
		exporter.setConfiguration(configuration);

		Injector injector = Guice.createInjector(new GuiceConfModule());
		injector.getInstance(TransactionManager.class).execute(connection -> {
			try {
				exporter.export(connection.getMetaData());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	static Type<?> classType(Class<?> classType) {
		try {
			return (Type<?>) classType.newInstance();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

}
