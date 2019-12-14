angular.module('treban')
    .controller('UserProfileController', ['$rootScope', '$scope', '$location','$window', UserProfileController]);

function UserProfileController($rootScope, $scope, $location, $window) {
    console.log("perfil de usuario");

    $window.document.title = "Treban - Perfil de usuario";

    $scope.highlightMenuItem = function($event) {
        $leftMenuItems = $window.document.querySelectorAll('.left-menu__item');

        if ($event.target.tagName !== "LI") {
            $currentLeftMenuItem = $event.target.parentElement;
        } else {
            $currentLeftMenuItem = $event.target;
        }

        $leftMenuItems.forEach(item => {
            item.classList.remove('active');
        });

        $currentLeftMenuItem.classList.add('active');
    };

    $scope.hideMainContainerContentItems = function () {
        $mainContainerContentItems = $window.document.querySelectorAll('.main-container__content-item');

        $mainContainerContentItems.forEach(item => {
            item.classList.remove('active');
        });
    };

    $scope.showProfilePersonalInfo = function ($event) {
        $mainContainerHeaderPrimaryText = $window.document.querySelector(".main-container__header-primary-text");
        $mainContainerHeaderSecondaryText = $window.document.querySelector(".main-container__header-secondary-text");

        $profilePersonalInfoContainer = $window.document.querySelector('#profile_personal_info');

        $scope.highlightMenuItem($event);
        $scope.hideMainContainerContentItems();

        $mainContainerHeaderPrimaryText.textContent = "Perfil";
        $mainContainerHeaderSecondaryText.textContent = "Gestiona tu información personal";

        $profilePersonalInfoContainer.classList.add('active');
    };

    $scope.showProfileEmail = function ($event) {
        $mainContainerHeaderPrimaryText = $window.document.querySelector(".main-container__header-primary-text");
        $mainContainerHeaderSecondaryText = $window.document.querySelector(".main-container__header-secondary-text");

        $profileEmailContainer = $window.document.querySelector('#profile_email');

        $scope.highlightMenuItem($event);
        $scope.hideMainContainerContentItems();

        $mainContainerHeaderPrimaryText.textContent = "Correo electrónico";
        $mainContainerHeaderSecondaryText.textContent = "Cambiar correo electrónico";

        $profileEmailContainer.classList.add('active');
    };

    $scope.showProfileSecurity = function ($event) {
        $mainContainerHeaderPrimaryText = $window.document.querySelector(".main-container__header-primary-text");
        $mainContainerHeaderSecondaryText = $window.document.querySelector(".main-container__header-secondary-text");

        $profileSecurityContainer = $window.document.querySelector('#profile_security');

        $scope.highlightMenuItem($event);
        $scope.hideMainContainerContentItems();

        $mainContainerHeaderPrimaryText.textContent = "Seguridad";
        $mainContainerHeaderSecondaryText.textContent = "Cambia tu contraseña";

        $profileSecurityContainer.classList.add('active');
    };
}