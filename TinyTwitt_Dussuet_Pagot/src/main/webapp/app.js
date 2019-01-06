var app = angular.module('tinytwitt', []);


app.controller('UtilisateurControle',function($scope,$http){
	var idusermess = 5629499534213120;
	$scope.postAjoutUtilisateur = function(){
		$http.put("http://localhost:8080/_ah/api/tinytwitt/v1/users",$scope.nomuti).then(function successCallback(response){
			$scope.nomuti="";
		}, function errorCallback(response){
			console.log("envoi des données raté");
		});
	}
	/**
	$scope.postAjoutMessage = function(){
		$http.put("http://urlapi.com/message",{message:$scope.message,utilisateur:$scope.monuti}).then(function successCallback(response){
			console.log("envoi des données réussi");
		}, function errorCallback(response){
			console.log("envoi des données raté");
		});
	}*/
	$scope.getMessages = function(){
		$http.get("http://localhost:8080/_ah/api/tinytwitt/v1/messages/"+ idusermess).then(function(response){
			$scope.data = response.data.items;
		});
	}
	$scope.getUsers = function(){
		$http.get("http://localhost:8080/_ah/api/tinytwitt/v1/users").then(function(response){
			$scope.dataUsers = response.data.items;
		});
	}
	$scope.getlistfollow = function(){
		$http.get("http://localhost:8080/_ah/api/tinytwitt/v1/users/"+idusermess+"/follows").then(function(response){
			$scope.dataF = response.data.items;
		});
	}
	$scope.addfollow = function(){
		$http.put("http://localhost:8080/_ah/api/tinytwitt/v1/users/"+idusermess+"/follows/"+$scope.idaddf).then(function successCallback(response){
			$scope.idaddf="";
		}, function errorCallback(response){
			println("envoi des données raté");
		});
		
	}
	$scope.unfollow = function(){
		$http.put("http://localhost:8080/_ah/api/tinytwitt/v1/users/"+idusermess+"/unfollow/"+$scope.idunf).then(function successCallback(response){
			$scope.idunf="";
		}, function errorCallback(response){
			console.log("envoi des données raté");
		});
	}
});