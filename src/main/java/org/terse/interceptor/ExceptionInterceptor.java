package org.terse.interceptor;

import java.lang.reflect.InvocationTargetException;

import com.opensymphony.xwork2.ActionInvocation;
/**
 * 处理异常的拦截器
 * 
 * 只处理来自BusinessException的异常
 */
public class ExceptionInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 2806631147459397242L;
	
	public String intercept(ActionInvocation invocation)throws Exception{
//		String result=null;
//		try{
//			result=invocation.invoke();
//		}catch(InvocationTargetException e){
//			if(e.getTargetException() instanceof BaseException){
//				return "ERROR";
//			}
//			e.printStackTrace();
//			throw e;
//		}catch(BaseException e){
//			return "ERROR";
//		}catch(Exception e){
//			e.printStackTrace();
//			throw e;
//		}
//		return result;
		
		return invocation.invoke();
	}
}
