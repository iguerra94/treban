angular.module('treban',
    [ 'ngRoute', 'ngSanitize', 'angularUtils.directives.dirPagination', 'pascalprecht.translate'])
    .constant('URL_BASE', '/')
    .constant('URL_API_BASE', '/api/v1/')
    .constant('RESPONSE_CODE_OK', 200)
    .constant('RESPONSE_CODE_CREATED', 201)
    .run(['$rootScope','$location', 'coreService',
        function($rootScope, $location, coreService) {
            console.log("Iniciando");
            moment.locale('es');

            $rootScope.cleanSigninData = function() {
                $rootScope.currentUser = {
                    name: "",
                    email: "",
                    username: "",
                    roles: []
                };

                $rootScope.autenticado = false;
            };

            $rootScope.cleanSigninData();

            //Callback luego de autenticación
            $rootScope.cbauth = false;

            $rootScope.authInfo = function (cb) {
                if (cb) $rootScope.cbauth = cb;

                coreService.authInfo()
                    .then(resp => {
                        if(resp.status === 200) {
                            $rootScope.currentUser.name = resp.data.name;
                            $rootScope.currentUser.email = resp.data.email;
                            $rootScope.currentUser.username = resp.data.username;
                            $rootScope.currentUser.roles = resp.data.roles;

                            $rootScope.autenticado = true;

                            if ($rootScope.cbauth) $rootScope.cbauth();
                        }else{
                            $rootScope.cleanSigninData();
                        }
                });
            };

            $rootScope.authInfo();

            $rootScope.logout = function(callAuthInfo) {
                coreService.logout().then(function(r){
                    $rootScope.cleanSigninData();

                    if (callAuthInfo) {
                        $rootScope.authInfo();
                    }

                    $rootScope.relocate("logout");
                }, function(){});
            };

            $rootScope.userMenuOpened = false;

            $rootScope.roles = { "DEVELOPER": "DEV", "PROJECT_LEADER": "PROJECT_LEAD" };

            $rootScope.currentListOfGreaterPositionCreated = 0;

            $rootScope.relocate = function(loc) {
                $location.path(loc);
            };

            $rootScope.isEmailValid = function(email) {
                return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
            };

        } ]);