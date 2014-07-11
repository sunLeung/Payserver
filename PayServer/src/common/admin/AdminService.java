package common.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.JsonUtils;
import utils.ReqUtils;
import utils.RespUtils;
import utils.StringUtils;

import common.config.App;
import common.config.AppContent;
import common.config.UnionsContent;
import common.logger.Logger;
import common.logger.LoggerManger;

public class AdminService {
	private static Logger log=LoggerManger.getLogger();
	
	public static void getAppsInfo(HttpServletRequest res,HttpServletResponse resp){
		RespUtils.jsonResp(resp, AppContent.appContent,"application/json;charset=UTF-8");
	}
	
	public static void getUnionsInfo(HttpServletRequest res,HttpServletResponse resp){
		RespUtils.jsonResp(resp, UnionsContent.unionsContent,"application/json;charset=UTF-8");
	}
	
	public static void createApp(HttpServletRequest res,HttpServletResponse resp){
		try {
			String postContent=ReqUtils.getPostString(res);
			if(StringUtils.isNotBlank(postContent)){
				int appid=AppContent.appContent.size()+1;
				App app=(App)JsonUtils.objectFromJson(postContent, App.class);
				app.setAppid(appid);
				app.initMapContent();
				AppContent.appContent.put(appid, app);
				AppContent.flush();
				RespUtils.commonResp(resp, RespUtils.CODE.SUCCESS);
				log.info("create app [appname"+ app.getAppname()+" appid="+app.getAppid()+"] succeed");
			}else{
				RespUtils.commonResp(resp, RespUtils.CODE.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RespUtils.commonResp(resp, RespUtils.CODE.EXCEPTION);
		}
	}
	
	public static void updateApp(HttpServletRequest res,HttpServletResponse resp){
		try {
			String postContent=ReqUtils.getPostString(res);
			if(StringUtils.isNotBlank(postContent)){
				App app=(App)JsonUtils.objectFromJson(postContent, App.class);
				app.initMapContent();
				
				App oldApp=AppContent.appContent.get(app.getAppid());
				if(oldApp!=null){
					AppContent.appContent.put(app.getAppid(), app);
					AppContent.flush();
					RespUtils.commonResp(resp, RespUtils.CODE.SUCCESS);
					log.info("update app [appname"+ app.getAppname()+" appid="+app.getAppid()+"] succeed");
					return;
				}
			}
			RespUtils.commonResp(resp, RespUtils.CODE.FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			RespUtils.commonResp(resp, RespUtils.CODE.EXCEPTION);
		}
	}
	public static void deleteAppById(HttpServletRequest res,HttpServletResponse resp){
		try {
			String postContent=ReqUtils.getPostString(res);
			if(StringUtils.isNotBlank(postContent)){
				int appid=Integer.valueOf(postContent);
				App oldApp=AppContent.appContent.get(appid);
				if(oldApp!=null){
					AppContent.appContent.remove(appid);
					AppContent.flush();
					RespUtils.commonResp(resp, RespUtils.CODE.SUCCESS);
					log.info("delete app [appname"+ oldApp.getAppname()+" appid="+oldApp.getAppid()+"] succeed");
					return;
				}
			}
			RespUtils.commonResp(resp, RespUtils.CODE.FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			RespUtils.commonResp(resp, RespUtils.CODE.EXCEPTION);
		}
	}
}
