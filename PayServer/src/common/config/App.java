package common.config;

import java.util.Map;

/**
 * 应用实体类
 * @author L
 *
 */
public class App {
	private int appid;
	private String appname;
	private Map<String,Map<String,String>> uniondate;
	private Map<String,String> servers;
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
	public Map<String, Map<String, String>> getUniondate() {
		return uniondate;
	}
	public void setUniondate(Map<String, Map<String, String>> uniondate) {
		this.uniondate = uniondate;
	}
	public Map<String, String> getServers() {
		return servers;
	}
	public void setServers(Map<String, String> servers) {
		this.servers = servers;
	}
}
