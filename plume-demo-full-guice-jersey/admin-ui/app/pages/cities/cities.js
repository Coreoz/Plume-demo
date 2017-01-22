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
		$state.go("app.cities.detail", {cityId: id});
	}

});

app.controller('citiesDetailController', function($stateParams, $state, uiService, citiesService, $translate) {
	
	var that = this;
	
	this.city = {};
	
	if($stateParams.cityId) {
		uiService
			.withPromise(citiesService.get($stateParams.cityId))
			.then(function(city) {
				that.city = city;
	
				$translate('cities.EDIT_TITLE', {name: city.name}).then(function(title) {
					that.title = title;
				});
			})
			.catch(angular.noop);
	} else {
		this.city.create = true;
	}

	this.save = function(city) {
		uiService
			.withPromise(citiesService.save(city), true)
			.then(function() {
				delete that.city.create;
			})
			.catch(angular.noop);
	};
	
	this.remove = function(city) {
		$translate('cities.DELETE_CONFIRM').then(function(message) {
			uiService
				.withConfirmation(message)
				.then(function() {
					uiService
						.withPromise(citiesService.delete(city.data.id), true)
						.then(function() {
							$state.go('app.cities.list');
						})
						.catch(angular.noop);
				})
				.catch(angular.noop);
		});
	};

});
