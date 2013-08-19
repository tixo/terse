package org.terse.interceptor;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.StrutsStatics;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public abstract class BaseInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 7936535753006004489L;

	protected HttpServletRequest getRequest(){
		return (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
	}
}
