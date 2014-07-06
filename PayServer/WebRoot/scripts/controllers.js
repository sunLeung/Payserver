app.controller('controller', ['$scope','service', function($scope,service) {
	//参数定义
	/**应用列表*/
	$scope.appsContent={};
	/**渠道列表(Map)*/
	$scope.unionMapContent = {};
	/**创建新应用实体*/
	$scope.newAppBean={};
	/**错误提示*/
	$scope.errorContent = {
		"gameListEmptyTip":"请配置相应道具发放服务器",
		"unionListEmptyTip":"请配置渠道",
		"appnameEmptyTip":""
	};
	
	//方法
	/**初始化应用列表方法*/
	$scope.loadApps=function(){
		service.getApps().success(function(data, status, headers, config) {
			for(index in data){
				var unions=[];
				for(unionid in $scope.unionMapContent){
					var union=$scope.unionMapContent[unionid];
					var obj={
						"unionid":unionid,
						"name":union.name,
						"uri":union.uri,
						"paramsArray":union.paramsArray,
						"isCompleted":false
					};
					for(param in union.paramsArray){
						obj[union.paramsArray[param]]="";
					}
					unions.push(obj);
				}
				var dataunion=data[index].uniondata;
				for(du in dataunion){
					var du_id= dataunion[du].unionid;
					for(uu in unions){
						if(du_id==unions[uu].unionid){
							var mark=true;
							for(p in unions[uu].paramsArray){
								var key=unions[uu].paramsArray[p];
								unions[uu][key]=dataunion[du][key];
								if(dataunion[du][key]=undefined||dataunion[du][key]==null||dataunion[du][key]==""){
									mark=false;
								}
							}
							unions[uu].isCompleted=mark;
						}
					}
				}
				data[index].uniondata=unions;
			}
			$scope.appsContent = data;
	  	}).error(function(data, status, headers, config) {
	  		console.log('error getAppsInfo status:'+status+'  data:'+data);
		});
	}

	
	/**初始化渠道列表*/
	service.getUnions().success(function(data, status, headers, config) {
		$scope.unionMapContent = data;
		for(index in $scope.unionMapContent){
			//初始化渠道列表(Array)
			var union=$scope.unionMapContent[index];
			var paramsArray=union.paramsArray=union.params.split(',');
		}
  	}).error(function(data, status, headers, config) {
  		console.log('error getUnions status:'+status+'  data:'+data);
	});
	
	/**初始化应用列表*/
	$scope.loadApps();
	
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
		var unions=$scope.newAppBean.uniondata;
		for(index in unions){
			if(unions[index].unionid==unionid){
				var union=unions[index];
				var params=union.paramsArray;
				var result=true;
				for(param in params){
					var p=union[params[param]];
					if(p==null||p==""){
						union.isCompleted=false;
						return;
					}
				}
				union.isCompleted=true;
			}
		}
	};
	
	/**初始化新应用数据*/
	$scope.initNewAppBean=function(){
		var bean={
			appname:"",
			servers:[],
			uniondata:[]
		};
		
		for(unionid in $scope.unionMapContent){
			var union=$scope.unionMapContent[unionid];
			var obj={
				"unionid":unionid,
				"name":union.name,
				"uri":union.uri,
				"paramsArray":union.paramsArray,
				"isCompleted":false
			};
			for(param in union.paramsArray){
				obj[union.paramsArray[param]]="";
			}
			bean.uniondata.push(obj);
		}
		$scope.newAppBean=bean;
		$scope.newAppBean.action="create";
		$scope.newAppBean.actionInfo="添加";
	}
	
	/**提交新应用*/
	$scope.submitApp=function(){
		var bean=$scope.newAppBean;
		//判断bean是否符合提交条件
		if(bean!=undefined&&bean!=null&&bean.appname!=undefined&&bean.appname!=null&&bean.appname!=""){
			var postBean={
				appname:bean.appname,
				servers:bean.servers,
				uniondata:[]
			};
			var unions=bean.uniondata;
			for(index in unions){
				var union=unions[index];
				if(union.isCompleted){
					var tu={
						unionid:union.unionid,
					};
					for(param in union.paramsArray){
						tu[union.paramsArray[param]]=union[union.paramsArray[param]];
					}
					postBean.uniondata.push(tu);
				}
			}
			
			if(bean.action=='create'){
				service.createApp(postBean).success(function(data, status, headers, config) {
					console.log('[info] createApp response:'+JSON.stringify(data));
					var code=data.code;
					if(code==0){
						//reload数据
						$scope.loadApps();
					}
			  	}).error(function(data, status, headers, config) {
			  		console.log('error createApp status:'+status+'  data:'+data);
				});
				return true;
			}else if(bean.action=='update'){
				postBean.appid=bean.appid;
				service.updateApp(postBean).success(function(data, status, headers, config) {
					console.log('[info] updateApp response:'+JSON.stringify(data));
					var code=data.code;
					if(code==0){
						//reload数据
						$scope.loadApps();
					}
			  	}).error(function(data, status, headers, config) {
			  		console.log('error updateApp status:'+status+'  data:'+data);
				});
				return true;
			}
		}
		return false;
	}
	
	/**打开应用信息界面*/
	$scope.openAppInfo=function(app){
		$scope.newAppBean=app;
		$scope.newAppBean.action="update";
		$scope.newAppBean.actionInfo="更新";
		$('#myModal').modal('show');
	};
	/**删除应用*/
	$scope.deleteApp=function(app){
		service.deleteAppById(app.appid).success(function(data, status, headers, config) {
			console.log('[info] deleteApp response:'+JSON.stringify(data));
			var code=data.code;
			if(code==0){
				//reload数据
				$scope.loadApps();
			}
	  	}).error(function(data, status, headers, config) {
	  		console.log('error deleteApp status:'+status+'  data:'+data);
		});
		return true;
	};
}]);
