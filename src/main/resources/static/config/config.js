angular.module('treban').config(
		function($routeProvider, $locationProvider, $httpProvider, $logProvider) {
			console.log('Configurando...');
			$logProvider.debugEnabled(true);
			
//			$httpProvider.defaults.withCredentials = true;
//			$httpProvider.interceptors.push('APIInterceptor');

			$locationProvider.html5Mode(true);

			$routeProvider
				.when('/', {
					templateUrl: 'views/landing/index.html',
					controller: 'LandingController'
				})
				.when('/home', {
					templateUrl: 'views/home/index.html',
					controller: 'HomeController'
				})
				.when('/user/profile', {
					templateUrl: 'views/user/profile/index.html',
					controller: 'UserProfileController'
				})
				.when('/signin', {
					templateUrl: 'views/signin/index.html',
					controller: 'SigninController'
				})
				.when('/signup', {
                    templateUrl: 'views/signup/index.html',
                    controller: 'SignupController'
                })
				.when('/signup/success', {
					templateUrl: 'views/signup/success/index.html',
					controller: 'SignupSuccessController'
				})
				.otherwise({
					redirectTo: '/'
				});
		});