'use strict';

app.service('sessionServiceWs', function ($http) {

	return {
		'create': function(credentials) {
			return $http({
				method: 'POST',
				url: '/api/admin/session',
				data: credentials
			})
			.then(function(response) {
				return response.data;
			});
		}
	}

});
