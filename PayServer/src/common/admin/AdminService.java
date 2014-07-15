package common.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;

import utils.JsonUtils;
import utils.ReqUtils;
import utils.RespUtils;
import utils.StringUtils;
import common.config.App;
import common.config.AppContent;
import common.config.PlatformUser;
import common.config.PlatformUserContent;
import common.config.UnionsContent;
import common.json.JSONObject;
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
	
	public static void login(HttpServletRequest res,HttpServletResponse resp){
		try {
			String postContent=ReqUtils.getPostString(res);
			if(StringUtils.isNotBlank(postContent)){
				JSONObject json=new JSONObject(postContent);
				String username=json.getString("username").trim();
				String password=json.getString("password").trim();
				PlatformUser user=PlatformUserContent.platformUserContent.get(username);
				if(user!=null&&user.getPassword().equals(password)){
					HttpSession session=res.getSession();
					session.setAttribute("loginUser", user);
					RespUtils.commonResp(resp, RespUtils.CODE.SUCCESS);
					return;
				}
			}
			RespUtils.commonResp(resp, RespUtils.CODE.FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			RespUtils.commonResp(resp, RespUtils.CODE.EXCEPTION);
		}
	}
	
	public static void logout(HttpServletRequest res,HttpServletResponse resp){
		try {
			HttpSession session=res.getSession();
			session.setAttribute("loginUser", null);
			RespUtils.commonResp(resp, RespUtils.CODE.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			RespUtils.commonResp(resp, RespUtils.CODE.EXCEPTION);
		}
	}
	
	public static void loadUser(HttpServletRequest res, HttpServletResponse resp) {
		try {
			JSONObject json = new JSONObject();
			HttpSession session = res.getSession();
			PlatformUser user = (PlatformUser) session.getAttribute("loginUser");
			if (user != null) {
				json.put("isLogin", true);
				json.put("username", user.getName());
				json.put("auth", user.getAuth());
			} else {
				json.put("isLogin", false);
			}
			RespUtils.stringResp(resp, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			RespUtils.commonResp(resp, RespUtils.CODE.EXCEPTION);
		}
	}
}
