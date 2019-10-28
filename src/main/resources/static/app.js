angular.module('treban',
    [ 'ngRoute', 'ngSanitize', 'angularUtils.directives.dirPagination'])
    .constant('URL_BASE', '/')
    .constant('URL_API_BASE', '/api/v1/')
    .constant('RESPONSE_CODE_OK', 200)
    .constant('RESPONSE_CODE_CREATED', 201)
    .run(['$rootScope','$location',
        function($rootScope, $location) {
            console.log("Iniciando");

            $rootScope.cleanLoginData = function() {
                $rootScope.autenticado = false;
                $rootScope.user = {
                    name : "",
                    password : "",
                    roles : []
                };
                $rootScope.user.roles = [];
            };

            $rootScope.cleanLoginData();

            $rootScope.relocate = function(loc) {
                $location.path(loc);
            };

            $rootScope.isEmailValid = function(email) {
                return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
            }

        } ]);