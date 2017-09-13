(function() {
	var app = angular.module("Login", []);

	
	app.directive("loginManager", function() {
		return {
			restrict:'E',
			templateUrl:'login.html',
			controller:function($http) {
				var self = this;
				self.logins = [];
			
				self.login = null;
			
				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/login'
					}).then(function(response) {
						self.logins = response.data;
					}, function(response) {
					});
				};
				
				self.add = function() {
					this.login = {};
				};
			
				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/login/'+id
					}).then(function(response) {
						self.login = response.data;
					}, function(response) {
					});
				};
			
				self.save = function() {
					if(self.login.id !=null) {
						$http({
							method : 'PUT',
							url : 'api/login/'+self.login.id,
							data : self.login
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.login);
						$http({
							method : 'POST',
							url : 'api/login/',
							data : self.login
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
						url : 'api/login/'+id
					}).then(function(response) {
						self.list();
					}, function(response) {
					});
					
				};
			
				self.cancel = function() {
					self.login = null;
					self.loginForm.$setPristine();
				};
				
				self.list();
			}, 
			controllerAs:'loginCtrl'
		};
	});
})();