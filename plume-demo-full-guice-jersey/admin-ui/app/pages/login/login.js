'use strict';

app.controller('loginController', function($state, sessionServiceWs, sessionService, i18nService) {

	var that = this;

	this.errorMessage = null;

	this.user = {
		userName: "",
		password: ""
	};

	this.login = function() {
		that.errorMessage = null;

		sessionServiceWs.create(that.user).then(function(response) {
			sessionService.login(response);
			$state.go('app.home');
		}, function(error) {
			i18nService.toMessage(error.data).then(function(message) {
				that.errorMessage = message;
			});
		});
	};

});
