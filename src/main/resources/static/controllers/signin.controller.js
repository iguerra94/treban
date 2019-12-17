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
        password: "",
    };

    $scope.password = "";

    $scope.animateSpinnerOne = function() {
        return new Promise(function(resolve, reject) {
            $scope.showSpinner($spinnerOne);

            // validations
            $scope.validateUndefinedAttributes();

            if (!$scope.validateEmptyAttributes([$scope.user.email])) {
                $window.setTimeout(() => {
                    $scope.hideSpinner($spinnerOne);
                    reject({ message: "Ninguno de los campos deben quedar vacios." });
                }, 500);
            }

            if (!$scope.validateEmail($scope.user.email)) {
                $window.setTimeout(() => {
                    $scope.hideSpinner($spinnerOne);
                    reject({ message: "El email ingresado no es valido." });
                }, 500);
            }

            $window.setTimeout(() => {
                $scope.hideSpinner($spinnerOne);
                resolve();
            }, 500);
        });
    };

    $scope.animateSpinnerTwo = function() {
        return new Promise(function(resolve, reject) {
            $scope.showSpinner($spinnerTwo);

            // validations
            $scope.validateUndefinedAttributes();

            if (!$scope.validateEmptyAttributes([$scope.password])) {
                $window.setTimeout(() => {
                    $scope.hideSpinner($spinnerTwo);
                    reject({ message: "Ninguno de los campos deben quedar vacios." });
                }, 500);
            }

            // hash the password, so that it travels hashed to the server.
            $scope.user.username = $scope.generateUsername();
            $scope.user.password = $scope.hashPassword($scope.password);

            $scope.password = "";

            $window.setTimeout(() => {
                $scope.hideSpinner($spinnerTwo);
                resolve();
            }, 500);
        });
    };

    $scope.validateClientDataInitialStep = function () {
        $scope.animateSpinnerOne()
            .then(() => {
                // validate if email exists in DB
                // If exists, go to final step
                // If not, display the error message
                $scope.verifyUserEmail($scope.user.email);
            })
            .catch((err) => {
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.message;
            });
    };

    $scope.validateClientDataFinalStep = function () {
        $scope.animateSpinnerTwo()
            .then(() => {
                // signin
                $scope.signin($scope.user);
            })
            .catch((err) => {
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.message;
            });
    };

    $scope.validateUndefinedAttributes = function() {
        if ($scope.user.email === undefined) {
            $scope.user.email = "";
        }

        if ($scope.password === undefined) {
            $scope.password = "";
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

    $scope.generateUsername = function() {
        return $scope.user.email.split("@")[0];
    };

    $scope.hashPassword = function(password) {
        const hashed = CryptoJS.SHA256(password);
        return hashed.toString(CryptoJS.enc.Hex);
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
                } else {
                    $errorMessageBox.classList.remove("hidden");
                    $errorMessage.textContent = resp.data.message;
                }
            })
            .catch(err => {
                console.log("err: ", err);
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.data.message;
            });
    };

    $scope.signin = function (user) {
        coreService.signin(user)
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    $rootScope.currentUser.name = resp.data.name;
                    $rootScope.currentUser.email = resp.data.email;
                    $rootScope.currentUser.username = resp.data.username;
                    $rootScope.currentUser.roles = resp.data.roles;

                    $rootScope.autenticado=true;

                    $rootScope.relocate("");
                } else {
                    $errorMessageBox.classList.remove("hidden");
                    $errorMessage.textContent = resp.data.message;

                    $rootScope.cleanSigninData();
                }
            })
            .catch(err => {
                $errorMessageBox.classList.remove("hidden");
                $errorMessage.textContent = err.data.message;

                $rootScope.cleanSigninData();
            })
    };
}