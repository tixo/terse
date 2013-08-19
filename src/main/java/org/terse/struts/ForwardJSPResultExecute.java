package org.terse.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.StrutsStatics;
import com.opensymphony.xwork2.ActionInvocation;

public class ForwardJSPResultExecute extends Result implements ResultExecute {

	private String jsp;
	
	protected ForwardJSPResultExecute(String jsp){
		this.jsp = jsp;
	}
	@Override
	public void doResultExecute(TResult tResult,ActionInvocation invocation) {
		HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		try {
			request.getRequestDispatcher(jsp).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
