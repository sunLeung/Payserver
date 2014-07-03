package common.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Response;
import common.config.AppContent;
import common.config.UnionsContent;

public class AdminService {
	
	public static void getAppsInfo(HttpServletRequest res,HttpServletResponse resp){
		Response.jsonResp(resp, AppContent.appContent,"application/json;charset=UTF-8");
	}
	
	public static void getUnionsInfo(HttpServletRequest res,HttpServletResponse resp){
		Response.jsonResp(resp, UnionsContent.unionsContent,"application/json;charset=UTF-8");
	}
}
