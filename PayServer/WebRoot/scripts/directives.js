//验证填写应用名
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

//监控渠道填写是否完成
app.directive('unionCheck',['service',function(service){
	return{
		link:function(scope,element,attrs){
			element.on('blur',function(){
				var unionid=element.attr('unionid');
				scope.isAddUnion(unionid);
			});
		}
	}
}]);