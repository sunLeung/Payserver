package common.config;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import utils.FileUtils;
import utils.JsonUtils;

import common.logger.Logger;
import common.logger.LoggerManger;

public class UnionsContent {
	private static Logger log=LoggerManger.getLogger();
	
	public static Map<String,Union> unionsContent=new ConcurrentHashMap<String,Union>();
	public static Map<String,Union> uriContent=new ConcurrentHashMap<String,Union>();
	
	public static void init(){
		log.info("Star init unions json data.");
		String filePath=Config.CONFIG_DIR + File.separator + "unions.json";
		String jsonSrc=FileUtils.readFileToJSONString(filePath);
		Union[] list=(Union[])JsonUtils.objectFromJson(jsonSrc, Union[].class);
		for(Union u:list){
			if(unionsContent.containsKey(u.getUnionid())){
				log.error("Find the same unionid "+u.getUnionid()+".");
				throw new IllegalArgumentException("Find the same unionid "+u.getUnionid()+".");
			}
			if(uriContent.containsKey(u.getUri())){
				log.error("Find the same uri "+u.getUri()+" in union:"+u.getUnionid()+".");
				throw new IllegalArgumentException("Find the same uri "+u.getUri()+" in union:"+u.getUnionid()+".");
			}
			if(IllegalCharContent.hasIllegalCharLike(u.getParams())){
				log.error("Find illegal char params "+u.getParams()+" in union:"+u.getUnionid()+".");
				throw new IllegalArgumentException("Find illegal char params "+u.getParams()+" in union:"+u.getUnionid()+".");
			}
			unionsContent.put(u.getUnionid(), u);
			uriContent.put(u.getUri(), u);
		}
		log.info("Init unions json data complete.");
	}
}
