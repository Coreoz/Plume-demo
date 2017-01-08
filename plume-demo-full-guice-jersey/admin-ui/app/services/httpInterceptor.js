'use strict';

app
.factory('httpInterceptor', function ($q, $rootScope, sessionService) {
	return {
		request: function (config) {
			// base URL
			if(config.url.indexOf('/api') == 0) {
				config.url = CONST.BASE_URL+config.url;
			}

			// access checking
			if(sessionService.isConnected()) {
				config.headers = config.headers || {}
				config.headers['Authorization'] = 'Bearer ' + sessionService.token();
			}
			
			return config;
		},
		response: function(response) {
			// TODO if a user-auth-token is sent, the session should be updated
			return response;
		},
		responseError: function(response) {
			if (response.status==401 || response.status==403) {
				$rootScope.$broadcast('UNAUTHORIZED', location);
			}
			return $q.reject(response);
		}
	};
})
.config(function ($httpProvider) {
	$httpProvider.interceptors.push('httpInterceptor');
});
