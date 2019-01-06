var app = angular.module('tinytwitt', []);


app.controller('UtilisateurControle',function($scope,$http){
	$scope.iduser = 5629499534213120;
	$scope.postAjoutUtilisateur = function(){
		$http.put("http://localhost:8080/_ah/api/tinytwitt/v1/users/"+ $scope.nomuti).then(function successCallback(response){
			
		}, function errorCallback(response){
			console.log("envoi des données rate");
		});
		$scope.nomuti="";
	}
	
	$scope.postAjoutMessage = function(){
		$http.post("http://localhost:8080/_ah/api/tinytwitt/v1/messages/"+$scope.mcontenu+"/"+$scope.miduti+"/"+$scope.mdate).then(function successCallback(response){
			$scope.getMessages();
		}, function errorCallback(response){
			console.log("envoi des données rate");
		});
		$scope.mcontenu="";
		$scope.miduti="";
		$scope.mdate="";
	}
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
		$http.get("http://localhost:8080/_ah/api/tinytwitt/v1/users/"+$scope.iduser+"/follows").then(function(response){
			$scope.dataF = response.data.items;
		});
	}
	$scope.addfollow = function(){
		$http.put("http://localhost:8080/_ah/api/tinytwitt/v1/users/"+$scope.iduser+"/follows/"+$scope.idaddf).then(function successCallback(response){
			$scope.getlistfollow();
			
		}, function errorCallback(response){
			println("envoi des données rate");
		});
		$scope.idaddf="";
	}
	$scope.unfollow = function(){
		$http.put("http://localhost:8080/_ah/api/tinytwitt/v1/users/"+$scope.iduser+"/unfollow/"+$scope.idunf).then(function successCallback(response){
			$scope.getlistfollow();
			
		}, function errorCallback(response){
			console.log("envoi des données rate");
		});
		$scope.idunf="";
	}
	/*
	$scope.getHashtags = function(){
		$http.get("http://localhost:8080/_ah/api/tinytwitt/v1/hashtags/"+$scope.hashtag).then(function(response){
			$scope.dataH = response.data.items;
		});
	}*/
});

