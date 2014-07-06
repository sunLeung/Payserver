package common.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import utils.StringUtils;

/**
 * 应用实体类
 * @author L
 *
 */
public class App {
	private int appid;
	private String appname;
	private Map<String,String>[] uniondate;
	private Map<String,String>[] servers;
	private Map<String,Map<String,String>> unionContent=new ConcurrentHashMap<String,Map<String,String>>();
	private Map<String,Map<String,String>> serverContent=new ConcurrentHashMap<String,Map<String,String>>();;
	public int getAppid() {
		return appid;
	}
	public void setAppid(int appid) {
		this.appid = appid;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public Map<String, String>[] getUniondate() {
		return uniondate;
	}
	public void setUniondate(Map<String, String>[] uniondate) {
		this.uniondate = uniondate;
	}
	public Map<String, String>[] getServers() {
		return servers;
	}
	public void setServers(Map<String, String>[] servers) {
		this.servers = servers;

	}
	public Map<String, Map<String, String>> unionContent() {
		return unionContent;
	}
	public Map<String, Map<String, String>> serverContent() {
		return serverContent;
	}
	public void initMapContent(){
		//初始化服务器检索表
		for(Map<String,String> map:servers){
			String sid=map.get("serverid");
			if(StringUtils.isNotBlank(sid))
				serverContent.put(sid, map);
		}
		//初始化渠道检索表
		for(Map<String,String> map:uniondate){
			String unionid=map.get("unionid");
			if(StringUtils.isNotBlank(unionid))
				unionContent.put(unionid, map);
		}
	}
}
