angular.module('treban')
    .controller('HomeController', ['$rootScope', '$scope', '$location','$window','$log', HomeController]);
// .controller('RegisterController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'user', RegisterController]);

// function RegisterController($rootScope, $scope, $location, $window, $log, coreService, user) {
function HomeController($rootScope, $scope, $location, $window, $log) {
    // console.log("register", $rootScope.user);
    // $scope.register = function () {
    //     console.log($scope.user);
    // };
    $scope.backlogTasks=["Tarea 1", "Tarea 2"];
    $log.log("HomeController");
}