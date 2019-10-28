angular.module('treban')
    .controller('SignupController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'RESPONSE_CODE_CREATED', SignupController]);
    // .controller('SignupController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'user', SignupController]);

// function SignupController($rootScope, $scope, $location, $window, $log, coreService, user) {
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
    };

    $scope.animateSpinner = function() {
        return new Promise(function(resolve, reject) {
            $scope.showSpinner();

            $window.setTimeout(() => {
                $scope.hideSpinner();
                resolve();
            }, 500);
        });
    };

    $scope.validateClientData = function () {
        const user = $scope.user;

        $scope.animateSpinner()
            .then(() => {
                // validations
                $scope.validateUndefinedAttributes(user);

                if (!$scope.validateEmptyAttributes(user)) return;
                if (!$scope.validateEmail(user.email)) return;

                // refine user data
                $scope.generateUsername(user);
                $scope.hashPassword(user);

                $scope.signup(user);
            })
     };

    $scope.validateUndefinedAttributes = function(user) {
        for (const prop in user) {
            if (user[`${prop}`] === undefined) {
                user[`${prop}`] = "";
            }
        }
    };

    $scope.validateEmptyAttributes = function(user) {
        if (user.name.length === 0 ||
            user.email.length === 0 ||
            user.password.length === 0) {

            $errorMessageBox.classList.remove("hidden");
            $errorMessage.textContent = "Ninguno de los campos deben quedar vacios.";

            return false;
        }
        return true;
    };

    $scope.validateEmail = function(email) {
        if (!$rootScope.isEmailValid(email)) {
            $errorMessageBox.classList.remove("hidden");
            $errorMessage.textContent = "El email ingresado no es valido.";

            return false;
        }
        return true;
    };

    $scope.generateUsername = function(user) {
        user.username = user.email.split("@")[0];
    };

    $scope.hashPassword = function(user) {
        const hashed = CryptoJS.SHA256(user.password);
        user.password = hashed.toString(CryptoJS.enc.Hex);
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
                }
            })
            .catch(err => {
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.data.message;
            });
    };
}
