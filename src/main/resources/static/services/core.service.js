angular.module('demo')
.factory('coreService',
	['$http','URL_BASE',function($http,URL_BASE){
			return {
				logout: function() {
					$http.get(URL_BASE+"logout");
				},
				authInfo: function() {
					 return $http.get(URL_BASE+"authinfo");
				},
				login: function(user) {
					var req = {
						method: 'POST',
						url: URL_BASE+'dologin',
						headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
						data: 'remember-me=true&username='+user.name+'&password='+user.password
					};
					return $http(req);
				},
				version: function() {
					 return $http.get(URL_BASE+"version");
				}
		}
	}
]);