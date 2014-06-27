package common.config;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.FileUtils;
import utils.JsonUtils;

public class AppContent {
	private static Log log=LogFactory.getLog(AppContent.class);
	
	public static Map<Integer,App> appContent=new ConcurrentHashMap<Integer,App>();
	
	public static void init(){
		log.info("Star init apps json data.");
		String filePath=Config.CONFIG_DIR + File.separator + "apps.json";
		String jsonSrc=FileUtils.readFileToString(filePath);
		App[] list=(App[])JsonUtils.objectFromJson(jsonSrc, App[].class);
		for(App a:list){
			if(appContent.containsKey(a.getAppid())){
				log.error("Find the same appid "+a.getAppid()+".");
				throw new IllegalArgumentException("Find the same appid "+a.getAppid()+".");
			}
			appContent.put(a.getAppid(), a);
		}
		log.info("Init apps json data complete.");
	}
	
	public static void main(String[] args) {
		init();
		System.out.println(appContent.get(1).getAppname());
		System.out.println(appContent.get(1).getUniondate().get("8888").get("key"));
	}
}
