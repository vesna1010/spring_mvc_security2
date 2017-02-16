var app = angular.module("myApp", []);
app.controller("myCtrl", function($scope) {
	$scope.canSubmit = false;
	$scope.notMatch = false;
	$scope.match = false;
	$scope.notCorrectPassword = false;
	var pattern = /^(\S){8,15}$/;

	$scope.checkPasswordMatch = function() {
		var password = $scope.password;
		var password1 = $scope.password1;
		
		//checking password valid while typing
		if(password!=undefined){
			if(password.match(pattern)==null){
				$scope.notCorrectPassword = true;
			}else{
				$scope.notCorrectPassword = false;
			}
		}
		
		//checking passwords match while typing
		if(password1!=undefined){
			if(password==password1 && !$scope.notCorrectPassword){
				$scope.canSubmit = true;
				$scope.notMatch = false;
				$scope.match = true;
			}else{
				$scope.canSubmit = false;
				$scope.notMatch = true;
				$scope.match = false;
			}
		}else{
			$scope.canSubmit = false;
			$scope.notMatch = false;
			$scope.match = false;
		}
	}
});