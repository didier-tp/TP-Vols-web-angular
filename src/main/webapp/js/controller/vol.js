(function() {
	var app = angular.module("Vol", []);

	app.directive("volManager", function() {
		return {
			restrict : 'E',
			templateUrl : 'vol.html',
			controller : function($http, $filter) {

				var self = this;

				self.vols = [];

				self.aeroports = [];

				self.vol = null;

				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/vol'
					}).then(function(response) {
						self.vols = response.data;
					}, function(response) {
					});
				};

				self.listAeroports = function() {
					$http({
						method : 'GET',
						url : 'api/aeroport'
					}).then(function(response) {
						self.aeroports = response.data;
					}, function(response) {
					});
				};

				self.add = function() {
					this.vol = {};
				};

				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/vol/' + id
					}).then(
							function(response) {
								self.vol = response.data;
								self.vol.dateDepart = new Date(
										self.vol.dateDepart);
								self.vol.dateArrivee = new Date(
										self.vol.dateArrivee);
								console.log(self.vol.heureDepart);
								self.vol.heureDepart = new Date(
										self.vol.heureDepart);
								console.log(self.vol.heureDepart);
								self.vol.heureArrivee = new Date(
										self.vol.heureArrivee);
								console.log(self.vol);
							}, function(response) {
							});
				};

				self.save = function() {
					if (self.vol.id != null) {
						console.log(self.vol);
						$http({
							method : 'PUT',
							url : 'api/vol/' + self.vol.id,
							data : self.vol
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.vol);
						$http({
							method : 'POST',
							url : 'api/vol/',
							data : self.vol
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
						url : 'api/vol/' + id
					}).then(function(response) {
						self.list();
					}, function(response) {
					});

				};

				self.cancel = function() {
					self.vol = null;
					self.volForm.$setPristine();
				};

				self.list();
				self.listAeroports();
			},
			controllerAs : 'volCtrl'
		};
	});
})();