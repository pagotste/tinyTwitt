var app = angular.module('tinytwitt', []);


app.controller('UtilisateurControle',function($scope,$http){
	$scope.iduser = 4510484191510528;
	$scope.tempo = 0;
	$scope.postAjoutUtilisateur = function(){
		var temps = Date.now();
		$http.put("https://tinytwitt-227717.appspot.com/_ah/api/tinytwitt/v1/users/"+ $scope.nomuti).then(function successCallback(response){
			temps = Date.now() - temps;
			$scope.tempo = temps;
		}, function errorCallback(response){
			console.log("envoi des données rate");
		});
		$scope.nomuti="";
	}
	
	$scope.postAjoutMessage = function(){
		var temps = Date.now();
		$http.post("https://tinytwitt-227717.appspot.com/_ah/api/tinytwitt/v1/messages/"+$scope.mcontenu+"/"+$scope.miduti+"/"+$scope.mdate).then(function successCallback(response){
			$scope.getMessages();
			temps = Date.now() - temps;
			$scope.tempo = temps;
		}, function errorCallback(response){
			console.log("envoi des données rate");
		});
		$scope.mcontenu="";
		$scope.miduti="";
		$scope.mdate="";
	}
	$scope.getMessages = function(){
		var temps = Date.now();
		$http.get(" https://tinytwitt-227717.appspot.com/_ah/api/tinytwitt/v1/messages/"+ $scope.iduser+"/"+10).then(function(response){
			$scope.data = response.data.items;
			temps = Date.now() - temps;
			$scope.tempo = temps;
		});
	}
	$scope.getUsers = function(){
		var temps = Date.now();
		$http.get(" https://tinytwitt-227717.appspot.com/_ah/api/tinytwitt/v1/users").then(function(response){
			$scope.dataUsers = response.data.items;
			temps = Date.now() - temps;
			$scope.tempo = temps;
		});
	}
	$scope.getlistfollow = function(){
		var temps = Date.now();
		$http.get(" https://tinytwitt-227717.appspot.com/_ah/api/tinytwitt/v1/users/"+$scope.iduser+"/follows").then(function(response){
			$scope.dataF = response.data.items;
			temps = Date.now() - temps;
			$scope.tempo = temps;
		});
	}
	$scope.addfollow = function(){
		var temps = Date.now();
		$http.put(" https://tinytwitt-227717.appspot.com/_ah/api/tinytwitt/v1/users/"+$scope.iduser+"/follows/"+$scope.idaddf).then(function successCallback(response){
			$scope.getlistfollow();
			temps = Date.now() - temps;
			$scope.tempo = temps;
		}, function errorCallback(response){
			console.log("envoi des données rate");
		});
		$scope.idaddf="";
	}
	$scope.unfollow = function(){
		var temps = Date.now();
		$http.put(" https://tinytwitt-227717.appspot.com/_ah/api/tinytwitt/v1/users/"+$scope.iduser+"/unfollow/"+$scope.idunf).then(function successCallback(response){
			$scope.getlistfollow();
			temps = Date.now() - temps;
			$scope.tempo = temps;
		}, function errorCallback(response){
			console.log("envoi des données rate");
		});
		$scope.idunf="";
	}
	/*
	$scope.getHashtags = function(){
		$http.get(" https://tinytwitt-227717.appspot.com/_ah/api/tinytwitt/v1/hashtags/"+$scope.hashtag).then(function(response){
			$scope.dataH = response.data.items;
		});
	}*/
});

