package common.config;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.FileUtils;
import utils.JsonUtils;

public class UnionsContent {
	private static Log log=LogFactory.getLog(UnionsContent.class);
	
	public static Map<String,Union> unionsContent=new ConcurrentHashMap<String,Union>();
	public static Map<String,Union> uriContent=new ConcurrentHashMap<String,Union>();
	
	public static void init(){
		log.info("Star init unions json data.");
		String filePath=Config.CONFIG_DIR + File.separator + "unions.json";
		String jsonSrc=FileUtils.readFileToString(filePath);
		Union[] list=(Union[])JsonUtils.objectFromJson(jsonSrc, Union[].class);
		for(Union u:list){
			if(unionsContent.containsKey(u.getUnionid())){
				log.error("Find the same unionid "+u.getUnionid()+".");
				throw new IllegalArgumentException("Find the same unionid "+u.getUnionid()+".");
			}
			if(uriContent.containsKey(u.getUri())){
				log.error("Find the same uri "+u.getUri()+".");
				throw new IllegalArgumentException("Find the same uri "+u.getUri()+".");
			}
			unionsContent.put(u.getUnionid(), u);
			uriContent.put(u.getUri(), u);
		}
		log.info("Init unions json data complete.");
	}
}
