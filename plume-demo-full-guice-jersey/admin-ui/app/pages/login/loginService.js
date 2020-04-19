'use strict';

app.service('sessionServiceWs', function ($resource) {

	var resource = $resource('/api/admin/session', null, {
		'create': { method: 'POST', isArray: false}
	});

	return {
		'create': function(credentials) {
			return resource
				.create(credentials)
				.$promise
				.then(function(result) {
					return result.webSessionToken;
				});
		}
	}

});
