'use strict';

var app = angular.module('admin',[
	'ngResource',
	'ngStorage',
	'ui.router',
	'pascalprecht.translate',
	'toaster',
	'ngDialog',
	'smart-table',
	'naif.base64'
])
.config(function($compileProvider) {
	$compileProvider.debugInfoEnabled(false);
})
.config(function($translateProvider) {
	$translateProvider
		.useSanitizeValueStrategy('escapeParameters')
		.translations('en', translations_en)
		.translations('fr', translations_fr)
		// tell the module what language to use by default
		.registerAvailableLanguageKeys(['en', 'fr'],
			{
				'fr*': 'fr',
				'en*': 'en',
				'*': 'en' // must be last!
			})
		.determinePreferredLanguage()
		.fallbackLanguage('en')
	;
})
.config(function($httpProvider) {
	// for IE caching : https://stackoverflow.com/a/19771501/3790208
	// initialize get if not there
	if (!$httpProvider.defaults.headers.get) {
		$httpProvider.defaults.headers.get = {};
	}

	// Answer edited to include suggestions from comments
	// because previous version of code introduced browser-related errors

	// disable IE ajax request caching
	$httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
	// extra
	$httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
	$httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
})
.run(function($rootScope, $state, sessionService, $translate) {
	$rootScope.$on("$stateChangeStart", function(event, toState) {
		if(!toState.public && !sessionService.isConnected()) {
			event.preventDefault();
			$state.transitionTo('access.login');
		}
	});
	
	$rootScope.$on("UNAUTHORIZED", function() {
		// TODO if disconnected, it would be better to show a popin to the user asking him what
		// to to do: going to the login or staying in the page
		$state.transitionTo("access.login");
	});
	
	$rootScope.hasPermission = sessionService.hasPermission;
	$rootScope.lang = $translate.proposedLanguage() || $translate.use() || $translate.preferredLanguage();
})
;
