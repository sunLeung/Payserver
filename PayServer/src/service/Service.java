package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Description 客户端-->服务器 数据包
 * @author liangyx
 * @date 2013-7-1
 * @version V1.0
 */
public abstract class Service implements Cloneable {

	/**
	 * 运行协议实现方法
	 */
	protected abstract void messageReceived(HttpServletRequest req,HttpServletResponse resp);
	public void handle(HttpServletRequest req,HttpServletResponse resp){
		messageReceived(req,resp);
	}

	public Service clone() {
		try {
			return (Service) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
