package org.terse.struts;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import org.terse.util.ReflectUtil;

public class RedirectResultExecute extends Result implements ResultExecute{
	
	private String path ; 
	
	protected RedirectResultExecute(String path){
		this.path = path;
	}
	
	@Override
	public void doResultExecute(TResult tResult,ActionInvocation invocation) {
		HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
    	String ctx=(String)ReflectUtil.invokeGet(invocation.getAction(),"getCtx");
    	try {
    		if(path.startsWith("/")){
    			response.sendRedirect(ctx+path);
			}else if(path.startsWith("http:")){
				response.sendRedirect(path);
			}else{
				response.sendRedirect(ctx+invocation.getProxy().getNamespace()+"/"+path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
