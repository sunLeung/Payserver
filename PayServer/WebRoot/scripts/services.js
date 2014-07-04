app.factory('service', ['$http',function($http) {
    var baseUrl = '/admin';
    return {
    	/**通过ID获取应用数据*/
        getAppInfoById: function(appid) {
            return $http.get(baseUrl + '/' + 'getAppInfoById'+ '/' +appid);
        },
        /**创建新应用*/
        createApp: function(appbean) {
            var url = baseUrl + '/' + 'createApp';
            return $http.post(url, appbean);
        },
        /**获取所有应用数据*/
        getApps: function() {
            return $http.get(baseUrl+'/'+'getAppsInfo');
        },
        /**通过ID更新应用数据*/
        updateAppById: function(appbean) {
            return $http.post(baseUrl + '/'+'updateAppById'+'/' + appbean.appid, appbean, {params: {charge: true}});
        },
        /**获取渠道数据*/
        getUnions:function(){
        	return $http.get(baseUrl+'/'+'getUnionsInfo');
        },
        /**创建应用时增加渠道*/
        pushUnion:function(union,scope){
        	var us=scope.newAppBean.uniondate;
        	removeById(union.unionid,us,'unionid',scope)
        	us.push(union);
        },
        /**获取渠道配置参数列表*/
        getUnionParams:function(unionid,scope){
        	var union=scope.unionMapContent[unionid];
        	return union.paramsArray;
        },
        /**通过ID删除指定数组里的对象*/
        removeById:function(id,array,indexKey,scope){
        	var i=-1;
        	for(index in array){
        		if(id==array[index][indexKey]){
        			i=index;
        		}
        	}
        	if(i>=0){
        		array.splice(i,1);
        	}
        }
    };
}]);