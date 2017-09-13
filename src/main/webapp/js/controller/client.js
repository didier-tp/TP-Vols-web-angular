(function() {
	var app = angular.module("Client", []);

	
	app.directive("clientManager", function() {
		return {
			restrict:'E',
			templateUrl:'client.html',
			controller:function($http) {
				var self = this;
				self.clients = [];
			
				self.client = null;
				
				self.type = null;

				self.setType = function(val) {
					self.type = val;
				};

				self.isType = function(val) {
					return this.type === val;
				};
			
				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/client'
					}).then(function(response) {
						self.clients = response.data;
					}, function(response) {
					});
				};
				
				
				
				self.addClientPhysique = function() {
					this.client = {};
					self.setType('Physique');
				};
				
				self.addClientMoral = function() {
					this.client = {};
					self.setType('Moral');
				};
				
				self.addClientEI = function() {
					this.client = {};
					self.setType('EI');
				};
			
				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/client/'+id
					}).then(function(response) {
						self.client = response.data;
						if (self.client.type ==="ClientPhysique"){
							self.setType('Physique');
						}else if(self.client.type ==="ClientMoral"){
							self.setType('Moral');
						}else if(self.client.type ==="ClientEI"){
							self.setType('EI');
						}
					}, function(response) {
					});
				};
			
				self.saveClientPhysique = function() {
					if(self.client.id !=null) {
						$http({
							method : 'PUT',
							url : 'api/clientPhysique/'+self.client.id,
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
							url : 'api/clientPhysique/',
							data : self.client
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					}
				};
				
				self.saveClientMoral = function() {
					if(self.client.id !=null) {
						$http({
							method : 'PUT',
							url : 'api/clientMoral/'+self.client.id,
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
							url : 'api/clientMoral/',
							data : self.client
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					}
				};
				
				self.saveClientEI = function() {
					if(self.client.id !=null) {
						$http({
							method : 'PUT',
							url : 'api/clientEI/'+self.client.id,
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
							url : 'api/clientEI/',
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
					self.setType(null);
					self.clientForm.$setPristine();
				};
				
				self.list();
			}, 
			controllerAs:'clientCtrl'
		};
	});
})();