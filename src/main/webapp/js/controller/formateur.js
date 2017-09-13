(function() {
	var app = angular.module("PremierPas-Reservation", []);

	app.directive("reservationManager", function() {
		return {
			restrict : 'E',
			templateUrl : 'reservation.html',
			controller : function($http) {
				var self = this;
				self.reservations = [];

				self.reservation = null;

				self.list = function() {
					$http({
						method : 'GET',
						url : 'api/reservation'
					}).then(function(response) {
						self.reservations = response.data;
					}, function(response) {
					});
				};

				self.add = function() {
					this.reservation = {};
				};

				self.edit = function(id) {
					$http({
						method : 'GET',
						url : 'api/reservation/' + id
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
							url : 'api/reservation/' + self.reservation.id,
							data : self.reservation
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.formateur);
						$http({
							method : 'POST',
							url : 'api/formateur/',
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
						url : 'api/formateur/' + id
					}).then(function(response) {
						self.list();
					}, function(response) {
					});

				};

				self.cancel = function() {
					self.formateur = null;
					self.formateurForm.$setPristine();
				};

				self.list();

			},
			controllerAs : 'formateurCtrl'
		};
	});
})();