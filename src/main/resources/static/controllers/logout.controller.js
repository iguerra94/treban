angular.module('treban')
    .controller('LogoutController', ['$rootScope', '$scope', '$location','$window', LogoutController]);

function LogoutController($rootScope, $scope, $location, $window) {
    console.log("logout");

    $window.document.title = "Treban - Cierre de sesion exitosa";
}
