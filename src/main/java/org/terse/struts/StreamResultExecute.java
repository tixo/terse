package org.terse.struts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;


public class StreamResultExecute extends Result implements ResultExecute{
	
	private ByteArrayOutputStream outStream;
	
	private String newName;
	
	private String result = T_RESULT;
	
	protected StreamResultExecute(ByteArrayOutputStream outStream,String newName){
		this.outStream = outStream;
		this.newName = newName;
	}
	
	@Override
	public void doResultExecute(TResult tResult, ActionInvocation invocation) {
		HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);

		ByteArrayInputStream in = null;
		OutputStream out = null ;
			try {
				byte[] outByte = outStream.toByteArray();
				in = new ByteArrayInputStream(outByte, 0, outByte.length);
				
				response.setContentType("APPLICATION/OCTET-STREAM"); 
				response.setHeader("Content-Disposition", "attachment; filename=\"" +  URLEncoder.encode(newName, "UTF-8") + "\"");
				int b = 0;
				byte[] buff = new byte[4096];
				out = response.getOutputStream();
				while((b=in.read(buff)) != -1) {
					out.write(buff, 0, b);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.flush();
					out.close();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					in = null;
					out = null;
					outStream = null;
				}
			}
		}
	
	@Override
	public String getResult() {
		return result;
	}

}
