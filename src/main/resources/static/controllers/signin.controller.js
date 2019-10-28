angular.module('treban')
    .controller('SignupController', ['$rootScope', '$scope', '$location','$window','$log', SignupController]);
    // .controller('SignupController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'user', SignupController]);

// function SignupController($rootScope, $scope, $location, $window, $log, coreService, user) {
function SignupController($rootScope, $scope, $location, $window, $log) {
    console.log("register", $rootScope.user);

    $window.document.title = "Treban - Registro";

    $scope.user = {
        name: "",
        email: "",
        username: "",
        password : ""
    };

    $scope.signup = function () {
        console.log($scope.user);
    };
}
