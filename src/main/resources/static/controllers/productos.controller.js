angular.module('demo')

.controller('productosController',
		function($rootScope,$scope, $http, $log, productosService) {
			$scope.titulo = "Productos";

			$scope.data = [];

			$scope.cleanInstance=function(){
				$scope.instancia = {
					vencimiento : new Date(),
					enStock:false, 
					enOferta:false
				};
				$scope.instanciaM =false;
			}

			$scope.refresh = function() {
				productosService.lista().then(function(resp) {
					if (resp.status == 200) {
						$scope.data = resp.data;
					} else {
						$scope.data = [];
					}
				}, function(e) {
					$log.error(e);
					$scope.data = [];
				});
			};
			
			$scope.modificar=function(p) {
				$scope.instanciaM=angular.copy(p);
			};
			$scope.guardar = function(agregar) {
				if (agregar) {
					productosService.agregar($scope.instancia).then(
						function(resp){
							if(resp.status==201) {
								$scope.refresh();
								$scope.cleanInstance();
							}
						},
						function(){}
					);
				} else {
					productosService.modificar($scope.instanciaM).then(
							function(resp){
								if(resp.status==200) {
									$scope.refresh();
									$scope.cleanInstance();
								}
							},
							function(){}
						);
				}

			};
			$scope.eliminar=function(id){
				if(confirm("Desea eliminar el producto seleccionado?")) {
					productosService.eliminar(id).then(
							function(resp){
								if(resp.status==200) {
									$scope.refresh();
								}
							},
							function(){}
						);
				}
					
			};
			
			$scope.mostrarGuardar = function(){
				return $scope.instancia.descripcion && 
					$scope.instancia.descripcion.length>2 && 
					$scope.instancia.precio && $scope.instancia.precio>0;
			};
			$rootScope.authInfo($scope.refresh);
			$scope.cleanInstance();
		});// End productosController
