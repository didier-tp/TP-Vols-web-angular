(function() {
	var app = angular.module("PremierPas",
			[ "Login", "Client", "Vol", "Aeroport", "CompagnieAerienne",
					"Passager", "Reservation", "Ville" ]);

	app.controller("HomeController", function($http) {
		self = this;
		self.tab = "home";

		self.setTab = function(val) {
			self.tab = val;
		};

		self.isTab = function(val) {
			return this.tab === val;
		};

		self.listTitrePhysique = function() {
			$http({
				method : 'GET',
				url : 'api/titrePhysique'
			}).then(function(response) {
				self.titrePhysique = response.data;
			}, function(response) {
			});
		};

		self.listTitreMoral = function() {
			$http({
				method : 'GET',
				url : 'api/titreMoral'
			}).then(function(response) {
				self.titreMoral = response.data;
			}, function(response) {
			});
		};
		self.listTitrePhysique();
		self.listTitreMoral();

	});
})();
