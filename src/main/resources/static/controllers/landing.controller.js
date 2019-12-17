angular.module('treban')
    .controller('LandingController', ['$rootScope', '$scope', '$location','$window','$log', LandingController]);

function LandingController($rootScope, $scope, $location, $window, $log) {
    console.log("landing");

    $window.document.title = "Treban - Bienvenido";
}
