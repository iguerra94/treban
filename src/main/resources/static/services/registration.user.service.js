angular.module('demo')
    .factory('registrationUserService',
        function($http,URL_API_BASE){
            return {
                addUser:function(p){
                    return $http.post(URL_API_BASE+'users',p);
                },
            }
        }
    );