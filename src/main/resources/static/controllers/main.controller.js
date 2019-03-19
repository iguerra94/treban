angular.module('demo')
.controller('homeController',function($scope,$rootScope,$location){
	$scope.titulo="Menú";
	$rootScope.authInfo();


});