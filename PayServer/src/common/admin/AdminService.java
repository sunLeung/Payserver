package common.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.JsonUtils;
import utils.RespUtils;
import utils.ResqUtils;
import utils.StringUtils;

import common.config.App;
import common.config.AppContent;
import common.config.UnionsContent;

public class AdminService {
	
	public static void getAppsInfo(HttpServletRequest res,HttpServletResponse resp){
		RespUtils.jsonResp(resp, AppContent.appContent,"application/json;charset=UTF-8");
	}
	
	public static void getUnionsInfo(HttpServletRequest res,HttpServletResponse resp){
		RespUtils.jsonResp(resp, UnionsContent.unionsContent,"application/json;charset=UTF-8");
	}
	
	public static void createApp(HttpServletRequest res,HttpServletResponse resp){
		try {
			String postContent=ResqUtils.getPostString(res);
			if(StringUtils.isNotBlank(postContent)){
				int appid=AppContent.appContent.size()+1;
				App app=(App)JsonUtils.objectFromJson(postContent, App.class);
				app.setAppid(appid);
				app.initMapContent();
				AppContent.appContent.put(appid, app);
				AppContent.flush();
				RespUtils.commonResp(resp, RespUtils.CODE.SUCCESS);
			}else{
				RespUtils.commonResp(resp, RespUtils.CODE.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RespUtils.commonResp(resp, RespUtils.CODE.EXCEPTION);
		}
	}
}
