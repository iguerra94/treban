angular.module('treban')
    .factory('coreService',
        ['$http', 'URL_BASE', 'URL_API_BASE',
            function($http, URL_BASE, URL_API_BASE) {
                return {
                    signup: function(user) {
                        return $http.post(`${URL_API_BASE}users`, user);
                    }
                }
            }
        ]);