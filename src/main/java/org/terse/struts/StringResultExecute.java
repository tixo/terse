package org.terse.struts;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.StrutsStatics;
import com.opensymphony.xwork2.ActionInvocation;

public class StringResultExecute extends Result implements ResultExecute {

	private String message;
	
	protected StringResultExecute (String message){
		this.message = message;
	}
	
	@Override
	public void doResultExecute(TResult tResult, ActionInvocation invocation) {
		HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
		OutputStream out =null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain;charset=utf-8");
			out= response.getOutputStream();
			out.write(message.getBytes("utf-8"));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
