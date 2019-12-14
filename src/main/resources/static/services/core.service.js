angular.module('treban')
    .factory('coreService',
        ['$http', 'URL_BASE', 'URL_API_BASE',
            function($http, URL_BASE, URL_API_BASE) {
                return {
                    signup: function(user) {
                        return $http.post(`${URL_API_BASE}users`, user);
                    },
                    verifyUserEmail: function (email) {
                        const config = {
                            params: { email }
                        };
                        return $http.get(`${URL_BASE}verify_email`, config);
                    },
                    signin: function(user) {
                        const req = {
                            url: URL_BASE+"signin",
                            method: "POST",
                            headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
                            data: 'remember-me=true&email='+user.username+'&password='+user.password
                        };
                        return $http(req);
                    }
                }
            }
        ]);