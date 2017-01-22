'use strict';

app.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/app/home');
	
	$stateProvider
		// public access
		.state('access', {
			url : '/access',
			template : '<div ui-view class="public-container"></div>',
			public : true
		})
		.state('access.login', {
			url : '/login',
			templateUrl : 'app/pages/login/login.html',
			public : true
		})

		// connected access
		.state('app', {
			abstract : true,
			url : '/app',
			templateUrl : 'app/layout/main.html',
			public : false
		})
		.state('app.home', {
			url : '/home',
			templateUrl : 'app/pages/home/home.html'
		})
		
		// users
		.state('app.users', {
			abstract : true,
			url : '/users',
			template : '<div ui-view></div>'
		})
		.state('app.users.list', {
			url : '/',
			templateUrl : 'app/pages/users/user-list.html'
		})
		.state('app.users.create', {
			url : '/new',
			templateUrl : 'app/pages/users/user-new.html'
		})
		.state('app.users.detail', {
			url : '/:userId',
			templateUrl : 'app/pages/users/user-detail.html'
		})

		// roles
		.state('app.roles', {
			url: '/roles',
			templateUrl: 'app/pages/roles/roles.html'
		})

		// cities
		.state('app.cities', {
			abstract : true,
			url : '/cities',
			template : '<div ui-view></div>'
		})
		.state('app.cities.list', {
			url : '/',
			templateUrl : 'app/pages/cities/city-list.html'
		})
		.state('app.cities.create', {
			url : '/new',
			templateUrl : 'app/pages/cities/city-detail.html'
		})
		.state('app.cities.detail', {
			url : '/:cityId',
			templateUrl : 'app/pages/cities/city-detail.html'
		})
		
		;
});
