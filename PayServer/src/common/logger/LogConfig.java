package common.logger;

public class LogConfig {
	/**************************日志等级定义********************************/
	public static final int INFO=0;
	public static final int DEBUG=1;
	public static final int ERROR=2;
	
	/**************************日志输出定义********************************/
	public static final int APPENDER_CONTROLLER=0;
	public static final int APPENDER_FILE=1;
	
	/**************************日志参数配置********************************/
	public static long LAST_MODIFY=0;
	public static final String SYSTEM_LOGGER_NAME="syslog";
	
	public static String LOG_PATH="d:/logs";
	public static String PATTERN="[%level %time %class %method %line] %msg";
	public static final int[] LEVELS=new int[]{INFO,DEBUG,ERROR};
	public static int[] APPENDERS=new int[]{APPENDER_CONTROLLER,APPENDER_FILE};
}
