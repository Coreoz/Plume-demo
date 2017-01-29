'use strict';

app.service('citiesService', function ($resource, $q) {
	
	var resource = $resource('/api/admin/city/:id', null, {
		'list': { method: 'GET', isArray: true},
		'save': { method: 'POST'},
		'delete': { method:'DELETE'}
	});
	
	var cities = null;
	
	var fetchCities = function() {
		return resource
			.list()
			.$promise
			.then(function(result) {
				cities = result;
				return cities;
			});
	};
	
	var currentCities = function() {
		if(cities != null) {
			return $q.resolve(cities);
		}
		
		return fetchCities();
	};
	
	return {
		'list': function() {
			return fetchCities();
		},
		'save': function(cityToSave) {
			return resource.save(cityToSave)
				.$promise
				.then(function(citySaved) {
					if(cityToSave.create && cities != null) {
						cities.push(citySaved);
					}
					
					return citySaved;
				});
		},
		'get': function(cityId) {
			return currentCities()
				.then(function(cities) {
					return cities.find(function(city) {
						return city.data.id == cityId;
					})
				});
		},
		'delete': function(id) {
			return resource.delete({id: id}).$promise;
		}
	}

});
