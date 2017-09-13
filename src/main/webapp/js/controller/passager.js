(function() {
	var app = angular.module("Login", []);

	
	app.directive("passagerManager", function() {
		return {
			restrict:'E',
			templateUrl:'passager.html',
			controller:function($http) {
				var self = this;
				self.passagers = [];
			
				self.passager = null;
			
				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/passager'
					}).then(function(response) {
						self.passagers = response.data;
					}, function(response) {
					});
				};
				
				self.add = function() {
					this.passager = {};
				};
			
				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/passager/'+id
					}).then(function(response) {
						self.passager = response.data;
					}, function(response) {
					});
				};
			
				self.save = function() {
					if(self.passager.id !=null) {
						$http({
							method : 'PUT',
							url : 'api/passager/'+self.passager.id,
							data : self.passager
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.passager);
						$http({
							method : 'POST',
							url : 'api/passager/',
							data : self.passager
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
						url : 'api/passager/'+id
					}).then(function(response) {
						self.list();
					}, function(response) {
					});
					
				};
			
				self.cancel = function() {
					self.passager = null;
					self.passagerForm.$setPristine();
				};
				
				self.list();
			}, 
			controllerAs:'passagerCtrl'
		};
	});
})();