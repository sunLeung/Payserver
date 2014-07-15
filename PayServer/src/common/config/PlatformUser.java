package common.config;

import utils.StringUtils;

public class PlatformUser {
	private int id;
	private String name;
	private String password;
	private String auth;
	/**权限数组*/
	private int[] authArray;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public int[] authArray() {
		return authArray;
	}
	public void initAuthArray(){
		if(StringUtils.isNotBlank(this.auth)){
			String[] as = this.auth.split(",");
			this.authArray=new int[as.length];
			for(int i=0;i<as.length;i++){
				this.authArray[i]=Integer.valueOf(as[i]);
			}
		}
	}
}
