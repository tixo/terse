package org.terse.struts;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.StrutsStatics;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.terse.exception.BusinessException;

public abstract class WebActionSupport extends ActionSupport {

	private static final long serialVersionUID = -3946900685263436510L;
	
	public Result doGet()throws Exception{
		throw new BusinessException(1);
	}
	
	public Result doPost()throws Exception{
		throw new BusinessException(2);
	}
	
	public HttpServletRequest getRequest(){
		return (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
	}
	
	public HttpServletResponse getResponse(){
		return (HttpServletResponse)ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
	}
	
	public Object getSessionAttribute(String key){
		return getRequest().getSession().getAttribute(key);
	}
	
	public void setSessionAttribute(String key,Object obj){
		getRequest().getSession().setAttribute(key, obj);
	}
}
