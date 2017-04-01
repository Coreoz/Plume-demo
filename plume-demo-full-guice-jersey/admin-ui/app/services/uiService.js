'use strict';

app.service('uiService', function($q, toaster, $translate, i18nService, ngDialog) {
	
	var self = this;
	
	this.withPromise = function(promise, showSuccess) {
		return promise
			.then(function(response) {
				if(showSuccess) {
					self.success();
				}
				return response;
			}, function(error) {
				if (!error || !error.data) {
					console.log(error);
					toaster.pop('error', '', "Unknown error");
					return $q.reject({});
				}

				return i18nService
					.toMessage(error.data)
					.then(function(message) {
						toaster.pop('error', '', message);
						return $q.reject(error);
					}, function () {
						toaster.pop('error', '', error.data);
						return $q.reject(error);
					});
			});
	};
	
	this.success = function(successMessage) {
		if(!successMessage) {
			$translate('message.SUCCESS').then(function(message) {
				toaster.pop('success', '', message);
			});
		} else {
			toaster.pop('success', '', successMessage);
		}
	}
	
	this.warning = function(warningMessage) {
		if(!warningMessage) {
			$translate('message.ERROR').then(function(message) {
				toaster.pop('warning', '', message);
			});
		} else {
			toaster.pop('warning', '', warningMessage);
		}
	}
	
	this.error = function(errorMessage) {
		if(!errorMessage) {
			$translate('message.ERROR').then(function(message) {
				toaster.pop('error', '', message);
			});
		} else {
			toaster.pop('error', '', errorMessage);
		}
	}
	
	this.withConfirmation = function(message) {
		return ngDialog
			.openConfirm({
				template: "app/layout/confirm.html", 
				className: 'ngdialog-theme-default',
				controller: function($scope) {
					$scope.message = message;
				}
			});
	}
	
});
