app.controller("addApp",function($scope,$http){
	$scope.app={};
	$scope.app.appname="";
	$scope.app.uniondate={};
	$scope.app.servers={};
});


app.controller('getAppsInfo', ['$scope','service', function($scope,service) {
	service.getApps().success(function(data, status, headers, config) {
		$scope.apps = data;
  	}).error(function(data, status, headers, config) {
  		cosole.log('error status:'+status+'  data:'+data);
	});
}]);

app.controller('createApp',['$scope','service',function($scope,service){
	service.createApp().success(function(data, status, headers, config) {
		$scope.apps = data;
  	}).error(function(data, status, headers, config) {
  		cosole.log('error status:'+status+'  data:'+data);
	});
}]);