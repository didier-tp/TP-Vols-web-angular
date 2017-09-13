(function () {
    var app = angular.module("PremierPas", []);

    app.controller("HomeController", function ($http) {
        self = this;
        self.tab = "home";

        self.setTab = function (val) {
            self.tab = val;
        };

        self.isTab = function (val) {
            return this.tab === val;
        };


    });
})();
