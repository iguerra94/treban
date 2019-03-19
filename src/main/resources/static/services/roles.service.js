angular.module('demo')
    .factory('rolesService',
        function($http,URL_API_BASE){
            return {
                listRoles:function(){
                    return $http.get(URL_API_BASE+'roles');
                },
                addRoles:function(p){
                    return $http.post(URL_API_BASE+'roles',r);
                },

            }
        }

        );