angular.module('demo')
    .factory('instanceLoginService',['$rootScope', '$scope','$log', 'coreService', '$location',  InstanceLoginFormController]);

function InstanceLoginFormController($rootScope, $scope, $log, coreService, $location) {

    /*$scope.init = function (){

        $scope.title="Ingreso";
        $scope.user=$rootScope.user;
        $rootScope.autenticado = false;
    }

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

*/
}
