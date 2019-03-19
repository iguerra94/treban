angular.module('demo')
    .controller('RegistrationFormUserController', ['$rootScope', '$scope', '$log', 'registrationUserService', 'coreService', 'rolesService', '$location', RegistrationFormUserController]);

function RegistrationFormUserController($rootScope, $scope, $log, registrationUserService, coreService, rolesService, $location) {

    // poner alert

    //$rootScope.role.idRole = "";

    $scope.cleanInputsUser = function () {

        $scope.name = "";
        $scope.email = "";
        $scope.user1 = "";
        $scope.password = "";
    }

    $scope.registrationUser = function () {


        var dataUser = {

            accountNonExpired: "true",
            accountNonLocked: "true",
            credentialsNonExpired: "true",
            enabled: "true",
            username: $scope.user1,
            email: $scope.email,
            password: $scope.password
        }

        registrationUserService.addUser(dataUser).then(
            function (resp) {

                if (resp.status == 201) {

                    /*
                    $rootScope.role = $rootScope.role1;

                    rolesService.addRoles().then(
                        function (resp) {

                            if (resp.status == 201){


                            }

                        },
                        function (err) {
                            $log.log(err);
                      }

                    );

        */

                    if (dataUser.username != null) {

                        var userFound = {

                            name: dataUser.username,
                            password: dataUser.password
                        }

                        $scope.userFound = userFound;

                        coreService.login($scope.userFound).then(
                            function (resp) {
                                if (resp.status === 200) {
                                    $rootScope.loginOpen = false;
                                    $rootScope.user.name = resp.data.name;
                                    $rootScope.user.mail = resp.data.mail;
                                    $rootScope.user.roles = resp.data.roles;
                                    $rootScope.autenticado = true;
                                    $location.path("/home");
                                    // poner alerta

                                    if ($rootScope.cbauth)
                                        $rootScope.cbauth();

                                } else {
                                    $rootScope.autenticado = false;
                                    $rootScope.user.roles = [];

                                }
                            },
                            function (respErr) {
                                $log.log(respErr);
                            }
                        );

                    }
                    else {

                        var userFound = {

                            name: dataUser.email,
                            password: dataUser.password
                        }

                        $scope.userFound = userFound;

                        coreService.login($scope.userFound).then(
                            function (resp) {
                                if (resp.status === 200) {
                                    $rootScope.loginOpen = false;
                                    $rootScope.user.name = resp.data.name;
                                    $rootScope.user.mail = resp.data.mail;
                                    $rootScope.user.roles = resp.data.roles;
                                    $rootScope.autenticado = true;
                                    $location.path("/home");
                                    // poner alerta

                                    if ($rootScope.cbauth)
                                        $rootScope.cbauth();

                                } else {
                                    $rootScope.autenticado = false;
                                    $rootScope.user.roles = [];

                                }
                            },
                            function (respErr) {
                                $log.log(respErr);
                            }
                        );
                        //$scope.cleanInputsUser();

                    }

                } else {
                    if (resp.status == 406) {

                        // poner alert
                        // indicar input con rojo
                    }

                }


            }, function (respErr) {
                $log.log(respErr);
            }
        );

    };

}

