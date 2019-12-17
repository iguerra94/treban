angular.module('treban')
    .service('APIInterceptor', function($rootScope) {
        const self = this;

        self.responseError = function(response) {
            console.log(response);
            if (response.status === 401) {
                $rootScope.autenticado = false;
            }  else {
                $rootScope.authInfo();
            }

            return response;
        };
    });