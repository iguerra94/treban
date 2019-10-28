angular.module('treban')
    .controller('SignupSuccessController', ['$rootScope', '$scope', '$location','$window','$log', SignupSuccessController]);
    // .controller('SignupController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'user', SignupController]);

// function SignupController($rootScope, $scope, $location, $window, $log, coreService, user) {
function SignupSuccessController($rootScope, $scope, $location, $window, $log) {
    console.log("signup success");

    $window.document.title = "Treban - Usuario registrado";
}
