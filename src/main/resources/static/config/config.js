angular.module('treban').config(
		function($routeProvider, $locationProvider, $httpProvider, $logProvider) {
			console.log('Configurando...');
			$logProvider.debugEnabled(true);
			
			// $httpProvider.defaults.withCredentials = true;
			//
			// $httpProvider.interceptors.push('APIInterceptor');
		
			$locationProvider.hashPrefix('!');
			
			$routeProvider
                .when('/', {
                    templateUrl : 'views/home/home.html',
                    controller : 'HomeController'
                })
                .when('/login', {
                    templateUrl : 'views/login.html',
                    controller : 'LoginController'
				})
				.when('/register', {
                    templateUrl: 'views/register.html',
                    controller: 'RegisterController'
                })
				.otherwise({
					redirectTo: '/'
				});
		});