'use strict';

app.controller('citiesListController', function(uiService, citiesService, $state) {
	
	var that = this;
	
	this.cities = [];
	
	uiService
		.withPromise(citiesService.list())
		.then(function(response) {
			that.cities = response;
		})
		.catch(angular.noop);

	this.displayCity = function(id) {
		$state.go('app.cities.tabs.generaldata', {cityId: id});
	}

});

app.controller('citiesTabsController', function($stateParams, uiService, citiesService, $rootScope, $translate) {
	
	var that = this;
	
	this.title = null;
	
	uiService
		.withPromise(citiesService.get($stateParams.cityId))
		.then(function(city) {
			that.title = $translate.instant('cities.DETAILS', {name :city.data.name});
		})
		.catch(angular.noop);
	
	this.tabs = [
		{
			label: $translate.instant('cities.tab.GENERAL_DATA'),
			state: "app.cities.tabs.generaldata",
			visible: $rootScope.hasPermission("CITIES_ALTER")
		},
		{
			label: $translate.instant('cities.tab.GALLERY'),
			state: "app.cities.tabs.gallery",
			visible: $rootScope.hasPermission("CITIES_GALLERY")
		}
	];
	
});

app.controller('citiesGeneralDataController', function($stateParams, $state, uiService, citiesService, $translate) {
	
	var that = this;
	
	this.city = {};
	this.title = null;
	
	if($stateParams.cityId) {
		uiService
			.withPromise(citiesService.get($stateParams.cityId))
			.then(function(city) {
				that.city = city;
			})
			.catch(angular.noop);
	} else {
		this.city.create = true;
	}

	this.save = function(city) {
		uiService
			.withPromise(citiesService.save(city), true)
			.then(function(citySaved) {
				if(that.city.create) {
					$state.go('app.cities.tabs.generaldata', {cityId: citySaved.data.id});
				} else {
					that.city = citySaved;
				}
			})
			.catch(angular.noop);
	};
	
	this.remove = function(city) {
		uiService
			.withConfirmation($translate.instant('cities.DELETE_CONFIRM'))
			.then(function() {
				uiService
					.withPromise(citiesService.delete(city.data.id), true)
					.then(function() {
						$state.go('app.cities.list');
					})
					.catch(angular.noop);
			})
			.catch(angular.noop);
	};

});
