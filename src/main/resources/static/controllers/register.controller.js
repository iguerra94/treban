angular.module('treban')
    .controller('RegisterController', ['$rootScope', '$scope', '$location','$window','$log', RegisterController]);
    // .controller('RegisterController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'user', RegisterController]);

// function RegisterController($rootScope, $scope, $location, $window, $log, coreService, user) {
function RegisterController($rootScope, $scope, $location, $window, $log) {
    console.log("register", $rootScope.user);
    $scope.register = function () {
        console.log($scope.user);
    };
}
