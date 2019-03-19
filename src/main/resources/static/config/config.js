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
		.when('/grafico',{
            templateUrl: 'views/grafico.html',
            controller: 'graficoController'
        })
        .when('/productos',{
			templateUrl: 'views/productos.html',
			controller: 'productosController'
		})
		.when('/c1',{
			templateUrl: 'views/vista1.html',
			controller: 'primeroController'
		})
		.when('/c2',{
			templateUrl: 'views/vista2.html',
			controller: 'segundoController'
		})
        .otherwise({
			redirectTo: '/home'
		});

});