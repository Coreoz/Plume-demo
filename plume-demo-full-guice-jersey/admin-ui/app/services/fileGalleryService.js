'use strict';

app.service('fileGalleryService', function ($resource) {
	
	var resource = $resource('/api/admin/galleries/:galleryType/:idFile', null, {
		'get': { method: 'GET', isArray: true},
		'add': { method: 'POST'},
		'delete': { method: 'DELETE'}
	});
	
	var that = this;
	
	this.get = function(galleryType, idData) {
		return resource.get({galleryType: galleryType, idData: idData}).$promise;
	};
	
	this.add = function(galleryType, idData, data) {
		return resource.add({galleryType: galleryType, idData: idData}, data).$promise;
	};
	
	this.delete = function(galleryType, idData, idFile) {
		return resource.delete({galleryType: galleryType, idData: idData, idFile: idFile}).$promise;
	};
	
	this.build = function(galleryType, idData) {
		return {
			'get': function() {
				return that.get(galleryType, idData);
			},
			'add': function(data) {
				return that.add(galleryType, idData, data);
			},
			'delete': function(idFile) {
				return that.delete(galleryType, idData, idFile);
			}
		};
	}

});
