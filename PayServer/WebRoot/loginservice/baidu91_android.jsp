<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="common.json.JSONObject"%>
<%@page import="utils.ReqUtils"%>
<%@page import="utils.Def"%>
<%@page import="common.logger.Logger"%>
<%@page import="common.logger.LoggerManger"%>
<%@page import="utils.RespUtils"%>
<%@page import="utils.StringUtils"%>
<%@page import="utils.HttpUtils"%>
<%@page import="utils.MD5"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="common.config.AppContent"%>
<%@page import="common.config.App"%>
<%@page import="common.config.UnionContent"%>
<%@page import="common.config.Union"%>
<!-- 百度91登陆验证 -->
<%
	Logger log=LoggerManger.getLogger("Baidu91_Android");
%>
<%
	String postStr = request.getAttribute("postContent").toString();
	System.out.println(postStr);
	JSONObject reqJSON=new JSONObject(postStr);
	String unionid=reqJSON.getString("unionid");
	int appid=reqJSON.getInt("appid");
	JSONObject data=reqJSON.getJSONObject("data");
	Union union=UnionContent.unionContent.get(unionid);
	App app=AppContent.appContent.get(appid);
	String AppId=app.getUnionParam(unionid, "AppId");
	String AppKey=app.getUnionParam(unionid, "AppKey");

	String uid=data.getString("uid");
	String SessionID=data.getString("SessionID");
	
	String rct = String.format("%s%s%s%s%s", AppId, "4", uid, SessionID, AppKey);
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("AppId", AppId);
	map.put("Act", "4");
	map.put("Uin", uid);
	map.put("Sign", MD5.encode(rct));
	map.put("SessionID", SessionID);
	String rc = HttpUtils.doPost("http://service.sj.91.com/usercenter/AP.aspx", map);
	log.info("baidu91_android auth result:"+rc);
	
	if(StringUtils.isNotBlank(rc)){
		JSONObject jb = new JSONObject(rc);
		if (jb.getInt("ErrorCode") == 1) {
	RespUtils.LoginAuthResp(response, Def.LoginSucceed, uid);
		}
	}
	RespUtils.LoginAuthResp(response, Def.LoginFail, "");
%>