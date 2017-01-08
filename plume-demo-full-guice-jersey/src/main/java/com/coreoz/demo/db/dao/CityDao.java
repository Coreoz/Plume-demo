package com.coreoz.demo.db.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.coreoz.demo.db.generated.City;
import com.coreoz.demo.db.generated.QCity;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

@Singleton
public class CityDao extends CrudDaoQuerydsl<City> {

	@Inject
	public CityDao(TransactionManagerQuerydsl transactionManager) {
		super(transactionManager, QCity.city, QCity.city.name.asc());
	}

}
