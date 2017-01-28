'use strict';

/**
 * tabs format :
 * [
 * 		{
 * 			label: "Tab name",
 * 			state: "app.users.info",
 * 			visible: $rootScope.hasPermission("SITE_EDIT_GENERAL")
 * 		}
 * ]
 * 
 * while "label" and "state" parameters are mandatory, "visible" parameter is optional and is true by default
 */
app
.directive('plumeTabs', function() {
	return {
		restrict: 'E',
		scope: {
			tabs: '=tabs'
		},
		templateUrl: 'app/directives/tabs/plm-tabs.html',
		controller: function($scope) {
			$scope.tabs.forEach(function(tab) {
				if(typeof tab.visible === 'undefined') {
					tab.visible = true;
				}
			});
		}
	};
});

