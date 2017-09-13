(function() {
	var app = angular.module("Vol", []);

	app.directive("volManager", function() {
		return {
			restrict : 'E',
			templateUrl : 'vol.html',
			controller : function($http) {

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
					}).then(function(response) {
						self.vol = response.data;
					}, function(response) {
					});
				};

				self.save = function() {
					if (self.vol.id != null) {
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