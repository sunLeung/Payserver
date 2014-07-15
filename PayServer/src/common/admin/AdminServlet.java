package common.admin;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.logger.Logger;
import common.logger.LoggerManger;

public class AdminServlet extends HttpServlet{
	private static Logger log=LoggerManger.getLogger();
	public static Map<String,Method> methods=new HashMap<String,Method>();
	
	public static void initMethods(){
		Method[] ms=AdminService.class.getMethods();
		for(Method m:ms){
			methods.put(m.getName(), m);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp){
		excute(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		excute(req, resp);
	}
	
	public void excute(HttpServletRequest req,HttpServletResponse resp){
		try {
			req.setCharacterEncoding("utf-8");
			resp.setHeader("content-type", "text/html;charset=UTF-8");
			String uri = req.getRequestURI();
			
			System.out.println(uri);
			
			
			if(uri.contains("/admin/")){
				String[] date=uri.split("/");
				String mkey=date[2];
				Method method=methods.get(mkey);
				if(method!=null){
					method.invoke(AdminService.class, req,resp);
				}else{
					resp.getWriter().write("undefine method.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}
}
