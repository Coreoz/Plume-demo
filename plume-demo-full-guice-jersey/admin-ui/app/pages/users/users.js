'use strict';

app.controller('usersListController', function(uiService, usersService, $state) {
	
	var that = this;
	
	this.users = [];
	
	uiService
		.withPromise(usersService.list())
		.then(function(response) {
			that.users = response;
			that.displayedUsers = [].concat(response);
		})
		.catch(angular.noop);

	this.displayUser = function(id) {
		$state.go("app.users.detail", {userId: id});
	}

});

app.controller('usersDetailController', function($stateParams, $state, uiService, usersService, $translate) {
	
	var that = this;
	
	this.user = {};
	this.rolesAvailable = [];

	uiService
		.withPromise(usersService.userWithRoles($stateParams.userId))
		.then(function(userWithRoles) {
			that.user = userWithRoles.user;
			that.rolesAvailable = userWithRoles.roles;

			$translate('users.EDIT_TITLE', {username: that.user.userName}).then(function(title) {
				that.title = title;
			});
		})
		.catch(angular.noop);

	this.save = function(user) {
		uiService
			.withPromise(usersService.update(user), true)
			.catch(angular.noop);
	};
	
	this.remove = function(user) {
		$translate('users.DELETE_CONFIRM').then(function(message) {
			uiService
				.withConfirmation(message)
				.then(function(arg) {
					uiService
						.withPromise(usersService.delete(user.id), true)
						.then(function() {
							$state.go('app.users.list');
						})
						.catch(angular.noop);
				})
				.catch(angular.noop);
		});
	};

});
app.controller('usersNewController', function($state, uiService, usersService, $translate) {
	
	var that = this;
	
	this.user = {create: true};
	this.rolesAvailable = [];

	uiService
		.withPromise(usersService.roles())
		.then(function(roles) {
			that.rolesAvailable = roles;
		})
		.catch(angular.noop);
	
	$translate('users.NEW_TITLE').then(function(title) {
		that.title = title;
	});
	
	this.save = function(user) {
		uiService
			.withPromise(usersService.save(user), true)
			.then(function(result) {
				$state.go('app.users.list');
			})
			.catch(angular.noop);
	};

});
