
app
.directive('plmPanel', function() {
	return {
		restrict : 'E',
		replace : true,
		transclude : true,
		scope : {
			title : '@'
		},
		templateUrl : 'app/directives/panel/plm-panel.html'
	};
})
.directive('plmPanelBody', function() {
	return {
		restrict : 'E',
		replace : true,
		transclude : true,
		scope : false,
		templateUrl : 'app/directives/panel/plm-panel-body.html'
	};
})
.directive('plmPanelFooter', function() {
	return {
		restrict : 'E',
		replace : true,
		transclude : true,
		scope : false,
		templateUrl : 'app/directives/panel/plm-panel-footer.html'
	};
})
.directive('plmFormItem', function() {
	return {
		restrict : 'E',
		replace : true,
		transclude : true,
		scope : {
			label : '@'
		},
		templateUrl : 'app/directives/panel/plm-form-item.html'
	};
})
;
