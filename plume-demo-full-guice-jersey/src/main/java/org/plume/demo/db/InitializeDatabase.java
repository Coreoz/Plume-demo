package org.plume.demo.db;

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
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setOutOfOrder(true);
		flyway.migrate();
	}

}
