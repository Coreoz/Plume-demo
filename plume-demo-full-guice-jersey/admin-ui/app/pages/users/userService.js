'use strict';

app.service('usersService', function ($resource, $q) {
	
	var resource = $resource('/api/admin/users/:id', null, {
		'list': { method: 'GET', isArray: false},
		'save': { method: 'POST', isArray: false},
		'update': { method:'PUT' },
		'delete': { method:'DELETE' }
	});
	
	var usersAndRoles = null;
	
	var currentUserWithRoles = function(id) {
		return {
			user: usersAndRoles
					.users
					.find(function(user) {
						return user.id == id;
					}),
			roles: usersAndRoles.roles
		};
	};
	
	var currentRoles = function() {
		return usersAndRoles.roles;
	};
	
	var fetchUsersAndRoles = function() {
		return resource
			.list()
			.$promise
			.then(function(result) {
				usersAndRoles = result;
				return angular.copy(result);
			});
	};
	
	return {
		'userWithRoles': function(id) {
			if(usersAndRoles != null) {
				return $q.resolve(currentUserWithRoles(id));
			}
			return fetchUsersAndRoles()
					.then(function() {
						return currentUserWithRoles(id);
					});
		},
		'roles': function() {
			if(usersAndRoles != null) {
				return $q.resolve(currentRoles());
			}
			return fetchUsersAndRoles()
					.then(function() {
						return currentRoles();
					});
		},
		'list': function() {
			return fetchUsersAndRoles()
					.then(function(usersAndRoles) {
						return usersAndRoles.users;
					});
		},
		'save': function(user) {
			return resource.save(user).$promise;
		},
		'update': function(user) {
			return resource.update(user).$promise;
		},
		'delete': function(id) {
			return resource.delete({id: id}).$promise;
		}
	}

});
