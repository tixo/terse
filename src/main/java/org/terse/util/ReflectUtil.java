package org.terse.util;

public class ReflectUtil {
	public static Object invokeGet(Object actionObject,String getMethodName) {
		try {
			return actionObject.getClass().getMethod(getMethodName).invoke(actionObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String namespaceToAllPath(String actionName){
   		String namespace = actionName.substring(0,actionName.lastIndexOf("."));
   		namespace=namespace.replace('.', '/');
   		return namespace;
   	}
	
	public static String getClassName(String actionName){
   		String className = actionName.substring(actionName.lastIndexOf(".")+1,actionName.length());
   		return className;
   	}
}
