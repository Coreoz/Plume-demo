'use strict';

app.controller('sideMenuController', function($rootScope) {

    $rootScope.showAsideNav = false;

    this.toggle = function() {
        $rootScope.showAsideNav = !$rootScope.showAsideNav;
    };

    this.reset = function() {
        $rootScope.showAsideNav = false;
    };

})
.controller('sideMenuDropdownController', function() {

    var closable = false;

    $("#aside-nav .nav-link").on('click', function() {
        closable = true;
    });

    $("#aside-nav .dropdown").on({
        "shown.bs.dropdown": function () {
            closable = false;
        },
        "hide.bs.dropdown": function (event) {
            return closable;
        }
    });
});