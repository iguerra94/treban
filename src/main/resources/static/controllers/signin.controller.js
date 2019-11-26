angular.module('treban')
    .controller('SigninController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'RESPONSE_CODE_OK', SigninController]);
    // .controller('SignupController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'user', SignupController]);

// function SignupController($rootScope, $scope, $location, $window, $log, coreService, user) {
function SigninController($rootScope, $scope, $location, $window, $log, coreService, RESPONSE_CODE_OK) {
    console.log("signin");

    $window.document.title = "Treban - Ingresar";

    $errorMessageBox = $window.document.querySelector(".error-message-box");
    $errorMessage = $window.document.querySelector(".error-message");
    $spinnerOne = $window.document.querySelector(".spinner-1");
    $spinnerTwo = $window.document.querySelector(".spinner-2");

    $scope.user = {
        email: "",
        username: "",
        password : ""
    };

    $scope.animateSpinnerOne = function() {
        return new Promise(function(resolve, reject) {
            $scope.showSpinner($spinnerOne);

            $window.setTimeout(() => {
                $scope.hideSpinner($spinnerOne);
                resolve();
            }, 500);
        });
    };

    $scope.animateSpinnerTwo = function() {
        return new Promise(function(resolve, reject) {
            $scope.showSpinner($spinnerTwo);

            $window.setTimeout(() => {
                $scope.hideSpinner($spinnerTwo);
                resolve();
            }, 500);
        });
    };

    $scope.validateClientDataInitialStep = function () {
        const user = $scope.user;

        $scope.animateSpinnerOne()
            .then(() => {
                // validations
                $scope.validateUndefinedAttributes(user);

                if (!$scope.validateEmptyAttribute(user.email)) return;
                if (!$scope.validateEmail(user.email)) return;

                // validate if email exists in DB
                // If exists, go to final step
                // If not, display the error message
                $scope.verifyUserEmail(user.email);
            })
    };

    $scope.validateClientDataFinalStep = function () {
        const user = $scope.user;

        $scope.animateSpinnerTwo()
            .then(() => {
                // validations
                $scope.validateUndefinedAttributes(user);

                if (!$scope.validateEmptyAttribute(user.password)) return;

                // hash the password, so that it travels hashed to the server.
                $scope.generateUsername(user);
                $scope.hashPassword(user);
                console.log(user);

                // signin
                $scope.signin(user);
            })

    };

    $scope.validateUndefinedAttributes = function(user) {
        for (const prop in user) {
            if (user[`${prop}`] === undefined) {
                user[`${prop}`] = "";
            }
        }
    };

    $scope.validateEmptyAttribute = function(attr) {
        if (attr.length === 0) {
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

    $scope.closeMessageBoxIfVisible = function() {
        if ($errorMessageBox.classList.contains("hidden")) return;
        $scope.closeMessageBox();
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

    $scope.showSpinner = function (spinner) {
        spinner.classList.remove("hidden");
    };

    $scope.hideSpinner = function (spinner) {
        spinner.classList.add("hidden");
    };

    $scope.goToFinalStep = function () {
        $signinInitialStepElements = $window.document.querySelectorAll(".signin-initial-step");
        $signinFinalStepElements = $window.document.querySelectorAll(".signin-final-step");
        $signinBoxTitle = $window.document.getElementById("signin-box-title");
        $fieldFinalStep = $window.document.querySelector(".field-final-step");

        $signinInitialStepElements.forEach((elem) => {
            $fieldFinalStep.textContent = "";
            elem.classList.add("hidden");
        });

        $signinFinalStepElements.forEach((elem) => {
            $fieldFinalStep.textContent = $scope.user.email;
            $signinBoxTitle.textContent = "Ahora, tu clave";
            elem.classList.remove("hidden");
        });
    };

    $scope.returnToInitialStep = function () {
        $signinInitialStepElements = $window.document.querySelectorAll(".signin-initial-step");
        $signinFinalStepElements = $window.document.querySelectorAll(".signin-final-step");
        $signinBoxTitle = $window.document.getElementById("signin-box-title");
        $emailInput = $window.document.getElementById("email");

        $signinFinalStepElements.forEach((elem) => {
            $fieldFinalStep.textContent = "";
            elem.classList.add("hidden");
        });

        $signinInitialStepElements.forEach((elem) => {
            $emailInput.value = $scope.user.email;
            $signinBoxTitle.textContent = "¡Hola! Ingresá tu email";
            elem.classList.remove("hidden");
        });
    };

    $scope.verifyUserEmail = function (email) {
        coreService.verifyUserEmail(email)
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    $scope.goToFinalStep();
                }
            })
            .catch(err => {
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.data.message;
            });
    };

    $scope.signin = function (user) {
        coreService.signin(user)
            .then(resp => {
                console.log(resp);
                if (resp.status === RESPONSE_CODE_OK) {
                    $rootScope.user.name = resp.data.name;
                    $rootScope.user.email = resp.data.email;
                    $rootScope.user.username = resp.data.username;
                    $rootScope.user.roles = resp.data.roles;
                    $rootScope.autenticado=true;
                    $scope.user.password = "";
                }
            })
            .catch(err => {
                console.log(err);
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.data.message;
                $rootScope.autenticado=false;
                $scope.user.password = "";
            });
    };
}