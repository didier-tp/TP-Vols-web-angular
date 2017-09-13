(function() {
	var app = angular.module("Login", []);

	
	app.directive("clientManager", function() {
		return {
			restrict:'E',
			templateUrl:'client.html',
			controller:function($http) {
				var self = this;
				self.clients = [];
			
				self.client = null;
			
				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/client'
					}).then(function(response) {
						self.clients = response.data;
					}, function(response) {
					});
				};
				
				self.add = function() {
					this.client = {};
				};
			
				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/client/'+id
					}).then(function(response) {
						self.client = response.data;
					}, function(response) {
					});
				};
			
				self.save = function() {
					if(self.client.id !=null) {
						$http({
							method : 'PUT',
							url : 'api/client/'+self.client.id,
							data : self.client
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.client);
						$http({
							method : 'POST',
							url : 'api/client/',
							data : self.client
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					}
				};
			
				self.remove = function(id) {
					$http({
						method : 'DELETE',
						url : 'api/client/'+id
					}).then(function(response) {
						self.list();
					}, function(response) {
					});
					
				};
			
				self.cancel = function() {
					self.client = null;
					self.clientForm.$setPristine();
				};
				
				self.list();
			}, 
			controllerAs:'clientCtrl'
		};
	});
})();