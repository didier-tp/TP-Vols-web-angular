(function() {
	var app = angular.module("Aeroport", []);

	app.directive("aeroportManager", function() {
		return {
			restrict : 'E',
			templateUrl : 'aeroport.html',
			controller : function($http) {
				var self = this;
				self.aeroports = [];

				self.aeroport = null;

				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/aeroport'
					}).then(function(response) {
						self.aeroports = response.data;
					}, function(response) {
					});
				};

				self.add = function() {
					this.aeroport = {};
				};

				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/aeroport/' + id
					}).then(
							function(response) {
								self.aeroport = response.data;
							}, function(response) {
							});
				};

				self.save = function() {
					if (self.aeroport.id != null) {
						$http({
							method : 'PUT',
							url : 'api/aeroport/' + self.aeroport.id,
							data : self.aeroport
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.aeroport);
						$http({
							method : 'POST',
							url : 'api/aeroport/',
							data : self.aeroport
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
						url : 'api/aeroport/' + id
					}).then(function(response) {
						self.list();
					}, function(response) {
					});

				};

				self.cancel = function() {
					self.aeroport = null;
					self.aeroportForm.$setPristine();
				};

				self.list();

			},
			controllerAs : 'aeroportCtrl'
		};
	});
})();