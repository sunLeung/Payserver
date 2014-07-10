package common.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.ArrayUtils;

public class Logger {
	private String loggerName;
	private String pattern;
	ConcurrentLinkedQueue<Log> clq = new ConcurrentLinkedQueue<Log>();

	public Logger(String loggerName, String pattern) {
		this.loggerName = loggerName;
		this.pattern = pattern;
	}

	public void info(String msg) {
		if(ArrayUtils.contains(LogConfig.LEVELS, LogConfig.INFO)){
			log(msg,LogConfig.INFO);
		}
	}

	public void debug(String msg) {
		if(ArrayUtils.contains(LogConfig.LEVELS, LogConfig.DEBUG)){
			log(msg,LogConfig.DEBUG);
		}
	}

	public void error(String msg) {
		if(ArrayUtils.contains(LogConfig.LEVELS, LogConfig.ERROR)){
			log(msg,LogConfig.ERROR);
		}
	}

	public void log(String msg, int level) {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		StackTraceElement s = stack[2];
		String className = s.getClassName();
		String methodName = s.getMethodName();
		String fileName = s.getFileName();
		int lineNum = s.getLineNumber();
		LocalDateTime logDateTime=LocalDateTime.now();
		String content = pattern.replace("%class", className)
				.replace("%line", lineNum + "")
				.replace("%time", logDateTime.toString()).replace("%msg", msg)
				.replace("%level", LoggerManger.getLevelString(level)).replace("%method", methodName).replace("%file", fileName);
		output(content,level,logDateTime);
	}
	
	public void output(String content, int level,LocalDateTime logDateTime){
		if(ArrayUtils.contains(LogConfig.APPENDERS, LogConfig.APPENDER_CONTROLLER)){
			if(level==LogConfig.INFO){
				System.out.println(content);
			}else if(level==LogConfig.DEBUG){
				System.out.println(content);
			}else if(level==LogConfig.ERROR){
				System.err.println(content);
			}
		}
		if(ArrayUtils.contains(LogConfig.APPENDERS, LogConfig.APPENDER_FILE)){
			Log log=new Log(content,logDateTime);
			clq.offer(log);
		}
	}

	public void flush() {
		File f = null;
		BufferedWriter bw = null;
		try {
			LocalDate tempDate = null;
			int t = 0;
			Log log = null;
			while ((log = clq.poll()) != null) {
				if (tempDate == null
						|| !log.getLogDateTime().toLocalDate()
								.isEqual(tempDate)) {
					f = new File(LogConfig.LOG_PATH + File.separator
							+ log.getLogDateTime().toLocalDate()
							+ File.separator + loggerName + ".log");
					if (!f.exists())
						f.getParentFile().mkdirs();
					bw = new BufferedWriter(new FileWriter(f, true));
					tempDate = log.getLogDateTime().toLocalDate();
				}
				bw.write(log.getOutputString());
				log.clear();
				log = null;
				t++;
				if (t % 50 == 0) {
					bw.flush();
				}
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
