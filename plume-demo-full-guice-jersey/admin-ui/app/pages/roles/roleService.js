'use strict';

app.service('roleService', function ($resource) {
	var resource = $resource('/api/admin/roles/:id', null, {
		'list': { method: 'GET', isArray: false},
		'save': { method: 'POST', isArray: false},
		'delete': { method: 'DELETE'},
	});
	return {
		'list': function() {
			return resource.list().$promise;
		},
		'save': function(role) {
			return resource.save(role).$promise;
		},
		'delete': function(id) {
			return resource.delete({id: id}).$promise;
		}
	}
});
