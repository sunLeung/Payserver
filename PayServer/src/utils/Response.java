package utils;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Response {
	private static Log log=LogFactory.getLog(Response.class);
	private static final String DEFAULT_CONTENT_TYPE="text/html;charset=UTF-8";
	
	public static void jsonResp(HttpServletResponse resp,Object obj){
		jsonResp(resp,obj,DEFAULT_CONTENT_TYPE);
	}
	public static void jsonResp(HttpServletResponse resp,Object obj,String contentType){
		try {
			resp.setHeader("content-type", contentType);
			String json=JsonUtils.encode2Str(obj);
			resp.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}
}
