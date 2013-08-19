package org.terse.log;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class LoggerFactory {
	
	private static Map<String,Logger> loggerMap = new HashMap<String,Logger>();
	
	protected static Logger getLogger(Class<?> clazz){
		Logger logger = loggerMap.get(clazz.getName());
		if(logger == null){
			logger = createLogger(clazz);
		}
		return logger;
	}
	
	private static Logger createLogger(Class<?> clazz){
		Logger logger = Logger.getLogger(clazz);
		putLogger(clazz.getName(),logger);
		return logger;
	}
	
	private static void putLogger(String className,Logger logger){
		loggerMap.put(className, logger);
	}
}
