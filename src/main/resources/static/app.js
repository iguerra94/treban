angular.module('treban',
    [ 'ngRoute', 'ngSanitize', 'angularUtils.directives.dirPagination', 'pascalprecht.translate'])
    .constant('URL_BASE', '/')
    .constant('URL_API_BASE', '/api/v1/')
    .constant('RESPONSE_CODE_OK', 200)
    .constant('RESPONSE_CODE_CREATED', 201)
    .run(['$rootScope','$location',
        function($rootScope, $location) {
            console.log("Iniciando");
            moment.locale('es');

            $rootScope.cleanSigninData = function() {
                $rootScope.autenticado = false;
                $rootScope.user = {
                    name: "",
                    email: "",
                    username: ""
                };
                $rootScope.user.roles = [];
            };

            $rootScope.cleanSigninData();

            //Callback luego de autenticación
            $rootScope.cbauth = false;

            $rootScope.userMenuOpened = false;

            $rootScope.relocate = function(loc) {
                $location.path(loc);
            };

            $rootScope.isEmailValid = function(email) {
                return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
            }

        } ]);