

angular.module('demo')
.controller('LoginFormController', ['$rootScope', '$scope','$log', 'coreService', '$location',  LoginFormController]);
function LoginFormController($rootScope, $scope, $log, coreService, $location) {


    $scope.title="Ingreso";
	$scope.user=$rootScope.user;
	$rootScope.autenticado = false;

	$scope.login = function () {
		coreService.login($scope.user).then(
			function(resp){ 
				if(resp.status===200) {
					$rootScope.loginOpen=false;
					$rootScope.user.name=resp.data.name;
					$rootScope.user.mail=resp.data.mail;
					$rootScope.user.roles = resp.data.roles;
					$rootScope.autenticado=true;
					$location.path("/home");
					// poner alerta

					if($rootScope.cbauth)
						$rootScope.cbauth();

				}else{
					$rootScope.autenticado=false;
					$rootScope.user.roles=[];

				}
			},
			function(respErr){
				$log.log(respErr);
			}
		);
	  };
}