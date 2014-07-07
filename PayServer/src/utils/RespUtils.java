package utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RespUtils {
	private static Log log=LogFactory.getLog(RespUtils.class);
	private static final String DEFAULT_CONTENT_TYPE="text/html;charset=UTF-8";
	/**返回码*/
	public static final class CODE{
		public static final int SUCCESS=0;
		public static final int FAIL=1;
		public static final int EXCEPTION=2;
	}
	
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
	
	public static void commonResp(HttpServletResponse resp,int code,String msg){
		String result="{\"code\":%s,\"msg\":%s}";
		try {
			result=String.format(result, code,msg);
			resp.setHeader("content-type", DEFAULT_CONTENT_TYPE);
			resp.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void commonResp(HttpServletResponse resp,int code){
		String result="{\"code\":%s}";
		try {
			result=String.format(result, code);
			resp.setHeader("content-type", DEFAULT_CONTENT_TYPE);
			resp.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void stringResp(HttpServletResponse resp,String content){
		try {
			resp.setHeader("content-type", DEFAULT_CONTENT_TYPE);
			resp.getWriter().write(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
