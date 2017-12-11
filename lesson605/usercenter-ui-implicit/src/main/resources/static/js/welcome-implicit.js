angular.module('welcome', [ 'ngResource', 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation'
    }).otherwise('/');
    
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation', function($rootScope, $scope, $http, $location, $route, $window) {
	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};
	
	console.log("login process...")

	$scope.credentials = {};
	$scope.login = function() {
        var OAUTH2_CLIENT_ID = "uc-implicit";
        var OAUTH2_REDIRECT_URI = "http://localhost:8084";
        var oauth2Scope = "read";

        var oauth2AuthEndpoint = "http://localhost:8082/oauth/authorize?"+"client_id="+OAUTH2_CLIENT_ID+"&redirect_uri="+OAUTH2_REDIRECT_URI+"&scope="+oauth2Scope+"&response_type=token&state=";
        window.open(oauth2AuthEndpoint,'callBackWindow','height=500,width=400');
        window.addEventListener("storage",function(event){
        	if (event.key == "accessToken"){
        		var accessToken = event.newValue;
        	}
        });

	};

}).controller('home', function($scope, $http, $window) {
    var headers = {
        "Accept" : "application/json", 
        authorization : "Bearer " + $window.sessionStorage.accessToken
    };
    
    $http.get("http://localhost:8082/api/roles/7", {
        headers : headers
    }).success(function(data) {
        $scope.role = data;
    })
});