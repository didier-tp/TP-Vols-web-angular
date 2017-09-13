(function() {
	var app = angular.module("Ville", []);

	app.directive("villeManager", function() {
		return {
			restrict : 'E',
			templateUrl : 'ville.html',
			controller : function($http) {
				var self = this;
				self.villes = [];

				self.ville = null;

				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/ville'
					}).then(function(response) {
						self.villes = response.data;
					}, function(response) {
					});
				};

				self.add = function() {
					this.ville = {};
				};

				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/ville/' + id
					}).then(function(response) {
						self.ville = response.data;
					}, function(response) {
					});
				};

				self.save = function() {
					if (self.ville.id != null) {
						$http({
							method : 'PUT',
							url : 'api/ville/' + self.ville.id,
							data : self.ville
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.formateur);
						$http({
							method : 'POST',
							url : 'api/ville/',
							data : self.formateur
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
						url : 'api/ville/' + id
					}).then(function(response) {
						self.list();
					}, function(response) {
					});

				};

				self.cancel = function() {
					self.ville = null;
					self.villeForm.$setPristine();
				};

				self.list();

			},
			controllerAs : 'villeCtrl'
		};
	});
})();