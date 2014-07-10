package common.logger;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggerManger {
	public static Map<String,Logger> loggerContent=new ConcurrentHashMap<String,Logger>();
	
	/**
	 * 获取相应的log
	 * @param loggerName
	 * @return
	 */
	public static Logger getLogger(String loggerName){
		if(LogConfig.SYSTEM_LOGGER_NAME.equals(loggerName)){
			throw new IllegalArgumentException(loggerName+" is system logger name.please try other logger name.");
		}
		
		Logger logger=loggerContent.get(loggerName);
		if(logger==null){
			logger=new Logger(loggerName,LogConfig.PATTERN);
			loggerContent.put(loggerName, logger);
		}
		return logger;
	}
	
	public static Logger getLogger(String loggerName,String pattern){
		if(LogConfig.SYSTEM_LOGGER_NAME.equals(loggerName)){
			throw new IllegalArgumentException(loggerName+" is system logger name.please try other logger name.");
		}
		
		Logger logger=loggerContent.get(loggerName);
		if(logger==null){
			logger=new Logger(loggerName,pattern);
			loggerContent.put(loggerName, logger);
		}
		return logger;
	}
	
	public static Logger getLogger(Class clazz){
		String loggerName=clazz.getName();
		return getLogger(loggerName);
	}
	
	public static Logger getLogger(){
		String loggerName=LogConfig.SYSTEM_LOGGER_NAME;
		Logger logger=loggerContent.get(loggerName);
		if(logger==null){
			logger=new Logger(loggerName,LogConfig.PATTERN);
			loggerContent.put(loggerName, logger);
		}
		return logger;
	}
	public static String getLevelString(int level){
		String l="";
		switch (level) {
		case 0:
			l="INFO";
			break;
		case 1:
			l="DEBUG";
			break;
		case 2:
			l="ERROR";
			break;
		default:
			break;
		}
		return l;
	}
	public static void loadLogConfig(String file){
		File f = new File(file);
		if(f.exists()){
			
		}else{
			throw new IllegalArgumentException("Can not find "+file);
		}
	}
}
