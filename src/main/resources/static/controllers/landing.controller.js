angular.module('treban')
    .controller('LandingController', ['$rootScope', '$scope', '$location','$window','$log', LandingController]);
    // .controller('SignupController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'user', SignupController]);

// function SignupController($rootScope, $scope, $location, $window, $log, coreService, user) {
function LandingController($rootScope, $scope, $location, $window, $log) {
    console.log("landing");

    $window.document.title = "Treban - Bienvenido";
}
