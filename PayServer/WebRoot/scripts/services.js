app.factory('service', ['$http', function($http) {
    var baseUrl = '/admin';
    return {
        getAppInfoById: function(appid) {
            return $http.get(baseUrl + '/' + 'getAppInfoById'+ '/' +appid);
        },
        createApp: function(appbean) {
            var url = baseUrl + '/' + 'createApp';
            return $http.post(url, appbean);
        },
        getApps: function() {
            return $http.get(baseUrl+'/'+'getAppsInfo');
        },
        updateAppById: function(appbean) {
            return $http.post(baseUrl + '/'+'updateAppById'+'/' + appbean.appid, appbean, {params: {charge: true}});
        }
    };
}]);