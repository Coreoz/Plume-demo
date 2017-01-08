'use strict';

app.service('sessionService', function ($sessionStorage) {
	
	var self = this;
	
	var currentUser = $sessionStorage.currentUser;
	
	var parseJwt = function(jwt) {
		return angular.fromJson(decodeURIComponent(escape(atob(jwt.split('.')[1]))));
	};
	
	this.session = function() {
		return currentUser.data;
	}
	
	this.token = function() {
		return currentUser.token;
	}
	
	this.login = function(jwt) {
		currentUser = {
			data: parseJwt(jwt),
			token: jwt
		};
		$sessionStorage.currentUser = currentUser;
	};
	
	this.logout = function() {
		currentUser = null;
		$sessionStorage.currentUser = null;
	};
	
	this.isConnected = function() {
		return !!currentUser;
	}
	
	var hasPermission = function(permission) {
		return self.isConnected() && self.session().permissions.indexOf(permission) >= 0;
	};
	
	this.hasPermission = function(permission) {
		if (permission) {
			return hasPermission(permission);
		}
		return false;
	};

});
