angular.module('treban')
    .controller('LoginController', ['$rootScope', '$scope', '$location','$window','$log', LoginController]);

function LoginController($rootScope, $scope, $location, $window, $log) {
	// $scope.title="Ingreso";
	// $scope.user=user;
	// $scope.login = function () {
    //     coreService.login(user).then(
    //         function(resp){
    //             if(resp.status===200) {
    //                 $rootScope.loginOpen=false;
    //                 $uibModalInstance.dismiss();
    //                 $rootScope.user.name=resp.data.username;
    //                 $rootScope.user.roles = resp.data.roles;
    //                 $rootScope.autenticado=true;
    //                 if($rootScope.cbauth)
    //                     $rootScope.cbauth();
    //             }else{
    //                 $rootScope.autenticado=false;
    //                 $rootScope.user.roles=[];
    //                 $rootScope.openLoginForm();
    //             }
    //         },
    //         function(respErr){
    //             $log.log(respErr);
    //         }
    //     );
    // };
}