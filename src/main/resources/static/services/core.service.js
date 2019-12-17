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
                            method: "POST",
                            url: `${URL_BASE}perform_signin`,
                            headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
                            data: 'remember-me=true&username='+user.username+'&password='+user.password
                        };
                        return $http(req);
                    },
                    logout: function() {
                        return $http.get(`${URL_BASE}logout`);
                    },
                    authInfo: function() {
                        return $http.get(`${URL_BASE}authinfo`);
                    }
                }
            }
        ]);