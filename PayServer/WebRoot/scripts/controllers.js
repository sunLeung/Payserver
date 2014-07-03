app.controller('getAppsInfo', ['$scope','service', function($scope,service) {
	service.getApps().success(function(data, status, headers, config) {
		$scope.apps = data;
  	}).error(function(data, status, headers, config) {
  		cosole.log('error getAppsInfo status:'+status+'  data:'+data);
	});
}]);

app.controller('getUnionsInfo', ['$scope','service', function($scope,service) {
	service.getUnions().success(function(data, status, headers, config) {
		$scope.unions = data;
  	}).error(function(data, status, headers, config) {
  		cosole.log('error getUnionsInfo status:'+status+'  data:'+data);
	});
}]);

app.controller('createApp',['$scope','service',function($scope,service){
	$scope.newAppBean={
		appname:"",
		servers:[],
		uniondate:[]
	};
	
	service.createApp().success(function(data, status, headers, config) {
		$scope.apps = data;
  	}).error(function(data, status, headers, config) {
  		cosole.log('error status:'+status+'  data:'+data);
	});
}]);