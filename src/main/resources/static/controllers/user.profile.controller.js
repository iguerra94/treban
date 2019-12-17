angular.module('treban')
    .controller('UserProfileController', ['$rootScope', '$scope', '$location','$window', UserProfileController]);

function UserProfileController($rootScope, $scope, $location, $window) {
    console.log("Perfil de usuario");

    $window.document.title = "Treban - Perfil de usuario";
}