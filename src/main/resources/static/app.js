angular.module('treban',
    [ 'ngRoute', 'ngSanitize', 'ngAnimate', 'ngTouch', 'ui.bootstrap',
        'ngSanitize', 'angularUtils.directives.dirPagination',
        'angucomplete-alt', 'ngLoadingSpinner', 'ui.select',
        'adaptv.adaptStrap', 'ngDragDrop', 'ui-notification',
        'chart.js', 'ngStomp', 'uiSwitch' ])
    .run(['$rootScope','$location','$stomp',
        function($rootScope, $location) {
            console.log("Iniciando");

            $rootScope.anioActual = new Date().getFullYear();

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

            console.log($rootScope.user);

            $rootScope.relocate = function(loc) {
                $location.path(loc);
            };
        } ]);