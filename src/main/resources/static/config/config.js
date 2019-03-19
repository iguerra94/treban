angular.module('demo')
.config(function($routeProvider,$httpProvider,$locationProvider){
	
	$httpProvider.interceptors.push('APIInterceptor');
	$httpProvider.defaults.withCredetials=true;

    $routeProvider
		.when('/home',{
			templateUrl: 'views/main.html',
			controller: 'homeController'
		})
        .when('/',{
            templateUrl: 'views/main.html',
            controller: 'homeController'
        })
        .otherwise({
			redirectTo: '/home'
		});

});