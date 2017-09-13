(function() {
	var app = angular.module("Reservation", []);

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
						url : 'api/reservation/' + id.toString()
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
							url : 'api/reservation/' + self.reservation.id.toString(),
							data : self.reservation
						}).then(function(response) {
							self.cancel();
							self.list();
						}, function(response) {
						});
					} else {
						console.log(self.reservation);
						$http({
							method : 'POST',
							url : 'api/reservation/',
							data : self.reservation
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
						url : 'api/reservation/' + id.toString()
					}).then(function(response) {
						self.list();
					}, function(response) {
					});

				};

				self.cancel = function() {
					self.reservation = null;
					self.reservationForm.$setPristine();
				};

				self.list();

			},
			controllerAs : 'reservationCtrl'
		};
	});
})();