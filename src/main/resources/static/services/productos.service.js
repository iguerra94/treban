angular.module('demo')
.factory('productosService',
	function($http,URL_API_BASE){
		return {
			lista:function(){
				return $http.get(URL_API_BASE+'productos');
			},
			agregar:function(p){
				return $http.post(URL_API_BASE+'productos',p);
			},
			modificar:function(p){
				return $http.put(URL_API_BASE+'productos',p);
			},
			eliminar:function(id){
				return $http.delete(URL_API_BASE+'productos/'+id);
			}
			
		}
	}
);