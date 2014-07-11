package common.route;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Service;
import utils.AnnoUtils;

import common.anno.Path;
import common.logger.Logger;
import common.logger.LoggerManger;

/**
 * 
 * @Description 路由控制器
 * @author liangyx
 * @date 2014-6-26
 * @version V1.0
 */
public class RouteController extends HttpServlet{
	private static Logger log=LoggerManger.getLogger();
	
	public static Map<String, Service> Paths = new ConcurrentHashMap<String, Service>();

	/**
	 * 协议解析器
	 */
	public static void parsePaths() {
		try {
			Set<Class<?>> set = AnnoUtils.getClasses("service");
			for (Class<?> clz : set) {
				Path handler = clz.getAnnotation(Path.class);
				if (handler != null) {
					String value = handler.value();
					if (Paths.get(value) != null){
						throw new IllegalArgumentException("[error] same path ('" + value + "' in "+ clz.getName() + " & "+ Paths.get(value).getClass().getName()+ ")");
					}
					Paths.put(value, (Service) clz.newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}
	
	
	/**
	 * 路由分发器
	 * @param session
	 * @param is
	 */
	public static void handlePath(HttpServletRequest req,HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("utf-8");
			resp.setHeader("content-type", "text/html;charset=UTF-8");
			String uri = req.getRequestURI();
			String key="";
			//是否请求支付服务器
			if(uri.contains("/callpay/")){
				String[] date=uri.split("/");
				String union=date[2];
				String appid=date[3];
				Service service=Paths.get(union);
				if(service!=null){
					Service clone=service.clone();
					clone.handle(Integer.valueOf(appid),req,resp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			try {
				resp.getWriter().write("Wrong uri request.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handlePath(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handlePath(req,resp);
	}
}
