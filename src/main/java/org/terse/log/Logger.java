package org.terse.log;

public class Logger {
	
	private Logger(){}
	
	public static void debug(String message){
		getLogger().debug(message);
	}
	
	public static void info(String message){
		getLogger().info(message);
	}
	
	public static void error(String message){
		getLogger().error(message);
	}
	
	public static void warn(String message){
		getLogger().warn(message);
	}
	
	private static org.apache.log4j.Logger getLogger(){
		Throwable throwable = new Throwable();
		String className = throwable.getStackTrace()[2].getClassName();
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return LoggerFactory.getLogger(clazz);
	}
}
