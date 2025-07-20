var app = angular.module('myApp', []);

app.controller('myCtrl', function($scope) {
	$scope.name = "John Doe";
	
	$scope.firstname = "John";
	$scope.changeName = function() {
    	$scope.firstname = "Nelly";
	}
});