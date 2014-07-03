app.directive('appNameValid',[function(){
	return{
		link:function(scope,element,attrs){
			scope.$watch('createAppForm.appname.$valid', function(newVal, oldVal) {
    			if(scope.createAppForm.appname.$dirty&&!newVal){
    				element.addClass('has-error');
    				scope.createAppForm.appname.errorInfo='应用名不能为空,且少于20个字符.';
    			}else{
    				element.removeClass('has-error');
    				scope.createAppForm.appname.errorInfo='';
    			}
    		});
		}
	};
}]);

app.directive('addGameServer',[function(){
	return{
		link:function(scope,element,attrs){
			scope.errorInfo={
				gameListEmptyTip:"请配置相应道具发放服务器",
				unionListEmptyTip:"请配置渠道"
			};
			var input=element.find('input');
			element.find('button').on('click',function(){
				var value=input.val();
				var values=value.split(';');
				if(value==null||value==""||values.length!=2){
					input.addClass('default-error');
					$(this).addClass('default-error');
				}else{
					var sid=values[0];
					var surl=values[1];
					
					for(index in scope.newAppBean.servers){
						if(scope.newAppBean.servers[index].id==sid){
							input.addClass('default-error');
							$(this).addClass('default-error');
							return;
						}
					}
					
					input.val('');
					var server={'id':sid,'url':surl};
					scope.errorInfo.gameListEmptyTip="";
					//创建新服务器数据
					scope.newAppBean.servers.push(server);
					scope.$apply();
				}
			});
			
			scope.tagGameListclick=function(index){
				scope.newAppBean.servers.splice(index,1);
				if(scope.newAppBean.servers.length<=0)
					scope.errorInfo.gameListEmptyTip="请配置相应道具发放服务器";
			}
			
			scope.tagUnionClick=function(index){
				scope.newAppBean.uniondate.splice(index,1);
				if(scope.newAppBean.uniondate.length<=0)
					scope.errorInfo.unionListEmptyTip="请配置渠道";
			}
			
			input.on('focus',function(){
				input.removeClass('default-error');
				element.find('button').removeClass('default-error');
			});
		}
	};
}]);