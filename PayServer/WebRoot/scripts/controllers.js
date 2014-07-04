app.controller('controller', ['$scope','service', function($scope,service) {
	//参数定义
	/**应用列表*/
	$scope.appsContent={};
	/**渠道列表(Map)*/
	$scope.unionMapContent = {};
	/**渠道临时数据容器*/
	$scope.unionTempContent={};
	/**创建新应用实体*/
	$scope.newAppBean={
		appname:"",
		servers:[
			{"serverid":"01","url":"http://192.168.1.187"},
			{"serverid":"02","url":"http://192.168.1.188"}
		],
		uniondate:[]
	};
	/**错误提示*/
	$scope.errorContent = {
		"gameListEmptyTip":"请配置相应道具发放服务器",
		"unionListEmptyTip":"请配置渠道"
	};
	
	//方法
	/**初始化应用列表*/
	service.getApps().success(function(data, status, headers, config) {
		$scope.appsContent = data;
  	}).error(function(data, status, headers, config) {
  		cosole.log('error getAppsInfo status:'+status+'  data:'+data);
	});
	
	/**初始化渠道列表*/
	service.getUnions().success(function(data, status, headers, config) {
		$scope.unionMapContent = data;
		for(index in $scope.unionMapContent){
			//初始化渠道列表(Array)
			var union=$scope.unionMapContent[index];
			var paramsArray=union.paramsArray=union.params.split(',');
			//初始化渠道临时数据容器
			var u={
				"unionid":union.unionid,
			};
			for(i in paramsArray){
				u[paramsArray[i]]="";
			}
			$scope.unionTempContent[union.unionid]=u;
		}
  	}).error(function(data, status, headers, config) {
  		cosole.log('error getUnions status:'+status+'  data:'+data);
	});
	
	/**添加新游戏服*/
	$scope.addServer=function(){
		var server={
			"serverid":null,
			"url":null
		};
		$scope.newAppBean.servers.push(server);
	};
	
	/**移除新游戏服*/
	$scope.removeServer=function(serverid){
		service.removeById(serverid,$scope.newAppBean.servers,'serverid',$scope);
	};
	
	/**判断是否配置好该渠道*/
	$scope.isAddUnion=function(unionid){
		$scope.newAppBean
	};
	
	$scope.initNewAppBean=function(){
		var bean=newAppBean={
			appname:"",
			servers:[],
			uniondate:{}
		};
	}
}]);
