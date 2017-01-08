'use strict';

app.service('i18nService', function ($translate, $q) {
	
	this.toMessage = function(wsError) {
		var args = {};
		var translateReference = $q.when(true);
		
		angular.forEach(wsError.statusArguments, function(value, key) {
			translateReference = translateReference.then(function() {
				return $translate(value)
				.then(function(label) {
					args["field"+key] = label;
				}, function() {
					args["field"+key] = value;
				});
			});
		});
		
		return translateReference.then(function() {
			return $translate('wsError.'+wsError.errorCode, args)
				.catch(function() {
					console.log("No translation for 'wsError." + wsError.errorCode + "'", args);
					return $translate('wsError.INTERNAL_ERROR', args);
				});
		});
	};
	
});
