package com.coreoz.demo.db;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

/**
 * Initialize the H2 database with SQL scripts placed in src/main/resources/db/migration
 */
@Singleton
public class InitializeDatabase {

	@Inject
	public InitializeDatabase(DataSource dataSource) {
		Flyway
			.configure()
			.dataSource(dataSource)
			.outOfOrder(true)
			.load()
			.migrate();
	}

}
