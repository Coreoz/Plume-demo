'use strict';

app
.controller('rolesController', function(roleService, uiService, $translate) {

	var that = this;
	
	this.edition = null;
	this.roles = [];
	var permissionsAvailable = [];
	
	var loadRolesAndPermissions = function() {
		uiService
			.withPromise(roleService.list())
			.then(function(data) {
				that.roles = data.rolesWithPermissions;
				permissionsAvailable = data.permissionsAvailable.sort();
				that.edition = null;
			})
			.catch(console.log);
	};
	loadRolesAndPermissions();
	
	this.edit = function(role) {
		var roleToEdit = angular.copy(role);
		that.edition = {
			role: roleToEdit,
			enabled: {
				list: roleToEdit.permissions,
				selected: []
			},
			available: {
				list: movePermissions([].concat(permissionsAvailable), [], roleToEdit.permissions),
				selected: []
			}
		};
		if(role.isNew) {
			$translate('roles.NEW_TITLE').then(function(label) { that.editTitle = label });
		} else {
			$translate('roles.DETAIL_TITLE', role).then(function(label) { that.editTitle = label });
		}
	};
	
	this.create = function() {
		that.edit({
			isNew: true,
			permissions: []
		});
	};
	
	this.cancel = function() {
		that.edition = null;
	};
	
	this.save = function() {
		uiService
			.withPromise(roleService.save(that.edition.role), true)
			.then(loadRolesAndPermissions)
			.catch(console.log);
	};
	
	this.delete = function() {
		$translate('roles.DELETE_CONFIRM').then(function(message) {
			uiService
				.withConfirmation(message)
				.then(function() {
					uiService
						.withPromise(roleService.delete(that.edition.role.idRole), true)
						.then(loadRolesAndPermissions)
						.catch(console.log);
				})
				.catch(console.log);
		});
	};
	
	this.disablePermissions = function() {
		movePermissions(that.edition.enabled.list, that.edition.available.list, that.edition.enabled.selected);
	};
	
	this.disableAllPermissions = function() {
		movePermissions(that.edition.enabled.list, that.edition.available.list, [].concat(that.edition.enabled.list));
	};
	
	this.enablePermissions = function() {
		movePermissions(that.edition.available.list, that.edition.enabled.list, that.edition.available.selected);
	};
	
	this.enableAllPermissions = function() {
		movePermissions(that.edition.available.list, that.edition.enabled.list, [].concat(that.edition.available.list));
	};
	
	// utils
	
	var movePermissions = function(from, to, permissionsToMove) {
		for (var i = 0; i < permissionsToMove.length; i++) {
			var permission = permissionsToMove[i];
			var indexToRemove = from.indexOf(permission);
			if(indexToRemove != -1) {
				from.splice(indexToRemove, 1);
				to.push(permission);
			}
		};
		return from;
	};

});
