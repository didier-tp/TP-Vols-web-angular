(function() {
	var app = angular.module("CompagnieAerienne", []);

	app.directive("compagnieAerienneManager", function() {
		return {
			restrict : 'E',
			templateUrl : 'compagnieAerienne.html',
			controller : function($http) {
				var self = this;
				self.compagniesAerienne = []; //

				self.compagnieAerienne = null;

				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/compagnieAerienne'
					}).then(function(response) {
						self.compagniesAerienne = response.data; //
					}, function(response) {
					});
				};

				self.add = function() {
					this.compagnieAerienne = {};
				};

				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/compagnieAerienne/' + id
					}).then(
							function(response) {
								self.reservation = response.data;
							}, function(response) {
							});
				};

				self.save = function() {
					if (self.reservation.id != null) {
						$http({
							method : 'PUT',
							url : 'api/compagnieAerienne/' + self.compagnieAerienne.id,
							data : self.compagnieAerienne
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.compagnieAerienne);
						$http({
							method : 'POST',
							url : 'api/compagnieAerienne/',
							data : self.compagnieAerienne
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
						url : 'api/compagnieAerienne/' + id
					}).then(function(response) {
						self.list();
					}, function(response) {
					});

				};

				self.cancel = function() {
					self.compagnieAerienne = null;
					self.compagnieAerienneForm.$setPristine();
				};

				self.list();

			},
			controllerAs : 'compagnieAerienneCtrl'
		};
	});
})();