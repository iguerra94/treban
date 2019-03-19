var app = angular.module('demo', ['ngRoute', 'ngTouch', 'ui.bootstrap',
        'ngSanitize','ui.select','angularUtils.directives.dirPagination',
        'ngLoadingSpinner', 'adaptv.adaptStrap', 'ui-notification',
        'chart.js', 'uiSwitch']);


app.constant('URL_API_BASE', '/api/v1/');
app.constant('URL_BASE', '/');
app.constant('URL_WS', '/api/v1/ws');

app.run(['$rootScope', '$log', '$location', '$uibModal', 'coreService' , function ($rootScope, $log, $location, $uibModal, coreService, $route) {
    //$log.log('Iniciando aplicación');
    //$rootScope.titulo="Valor por defecto";
    $rootScope.relocate=function(loc){
    	//$location.path(loc);
        $route.reload();
    };



    $rootScope.cleanLoginData = function () {
        $rootScope.autenticado = false;
        $rootScope.user = {
            name: "",
            mail: "",
            password: "",
            roles: []
        };
    };

    $rootScope.cbauth = false;
    $rootScope.authInfo = function (cb) {
        if (cb)
            $rootScope.cbauth = cb;
        coreService.authInfo().then(function (resp) {
            if (resp.status === 200) {
                $rootScope.user.name = resp.data.name;
                $rootScope.user.mail = resp.data.mail;
                $rootScope.user.roles = resp.data.roles;
                $rootScope.autenticado = true;
                if ($rootScope.cbauth)
                    $rootScope.cbauth();
            } else {
                $rootScope.cleanLoginData();
            }
        });
    }
    coreService.version().then(
        function (resp) {
            $rootScope.softwareVersion = resp.data.version;
        }
    );
    $rootScope.logout = function () {
        coreService.logout().then(function (r) {
            $rootScope.cleanLoginData();
            $location.path("/");

        }, function () {
        });
    };
    $rootScope.cleanLoginData();
}]);
