angular.module('treban')
    .controller('SignupController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'RESPONSE_CODE_CREATED', SignupController]);

function SignupController($rootScope, $scope, $location, $window, $log, coreService, RESPONSE_CODE_CREATED) {
    console.log("signup");

    $window.document.title = "Treban - Registro";

    $errorMessageBox = $window.document.querySelector(".error-message-box");
    $errorMessage = $window.document.querySelector(".error-message");
    $spinner = $window.document.querySelector(".fa-spinner");

    $scope.user = {
        name: "",
        email: "",
        username: "",
        password : "",
        actualRoleName: ""
    };

    $scope.password = "";
    $scope.userRole = "";

    $scope.animateSpinner = function() {
        return new Promise(function(resolve, reject) {
            $scope.showSpinner();

            // validations
            $scope.validateUndefinedAttributes();

            if (!$scope.validateEmptyAttributes([$scope.user.name, $scope.user.email, $scope.password, $scope.userRole])) {
                $window.setTimeout(() => {
                    $scope.hideSpinner();
                    reject({ message: "Ninguno de los campos deben quedar vacios." });
                }, 500);
            }

            if (!$scope.validateEmail($scope.user.email)) {
                $window.setTimeout(() => {
                    $scope.hideSpinner();
                    reject({ message: "El email ingresado no es valido." });
                }, 500);
            }

            // refine user data
            $scope.user.username = $scope.generateUsername($scope.user.email);
            $scope.user.password = $scope.hashPassword($scope.password);
            $scope.user.actualRoleName = $scope.userRole;

            $scope.password = "";

            $window.setTimeout(() => {
                $scope.hideSpinner();
                resolve();
            }, 500);
        });
    };

    $scope.validateClientData = function () {
        $scope.animateSpinner()
            .then(() => {
                $scope.signup($scope.user);
            })
            .catch((err) => {
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.message;
            });
     };

    $scope.validateUndefinedAttributes = function() {
        if ($scope.user.name === undefined) {
            $scope.user.name = "";
        }

        if ($scope.user.email === undefined) {
            $scope.user.email = "";
        }

        if ($scope.password === undefined) {
            $scope.password = "";
        }

        if ($scope.userRole === undefined) {
            $scope.userRole = "";
        }
    };

    $scope.validateEmptyAttributes = function(attrs) {
        for (let attr of attrs) {
            if (attr.length === 0) {
                return false;
            }
        }
        return true;
    };

    $scope.validateEmail = function(email) {
        return $rootScope.isEmailValid(email);
    };

    $scope.closeMessageBoxIfVisible = function() {
        if ($errorMessageBox.classList.contains("hidden")) return;
        $scope.closeMessageBox();
    };

    $scope.generateUsername = function(email) {
        return email.split("@")[0];
    };

    $scope.hashPassword = function(password) {
        const hashed = CryptoJS.SHA256(password);
        return hashed.toString(CryptoJS.enc.Hex);
    };

    $scope.closeMessageBox = function () {
        $errorMessageBox.classList.add("hidden");
    };

    $scope.showSpinner = function () {
        $spinner.classList.remove("hidden");
    };

    $scope.hideSpinner = function () {
        $spinner.classList.add("hidden");
    };

    $scope.signup = function (user) {
        coreService.signup(user)
            .then(resp => {
                if (resp.status === RESPONSE_CODE_CREATED) {
                    $rootScope.relocate("signup/success")
                } else {
                    $errorMessageBox.classList.remove("hidden");
                    $errorMessage.textContent = resp.data.message;
                }
            })
            .catch(err => {
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.data.message;
            });
    };
}