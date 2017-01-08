'use strict';

app.controller('userMenuController', function(sessionService) {

	this.userFullname = sessionService.session().fullName;

});
