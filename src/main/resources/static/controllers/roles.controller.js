angular.module('demo').controller('RoleController', ['$rootScope', '$scope','$log', 'rolesService',  RolesController]);
function RolesController($rootScope, $scope, $log, rolesService) {



    rolesService.listRoles().then(function (resp) {

            if (resp.status == 200) {

                $scope.roles = resp.data;

            }
            else {
                $scope.roles = [];
            }
        },
        function (respErr) {
            $log.log(respErr);
        }
    );

        //$scope.role1 = $scope.role.selected;

}