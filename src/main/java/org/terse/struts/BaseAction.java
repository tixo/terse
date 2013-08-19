package org.terse.struts;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;

@SuppressWarnings("serial")
@ParentPackage("system")
public abstract class BaseAction extends WebActionSupport{

	protected Map<String,String> errorMap=new HashMap<String,String>();
	
	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	
	public Collection<String> getErrorList(){
		return errorMap.values();
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}

	public String getCtx(){
		return getRequest().getContextPath();
	}
	
	private Result result = null;
	
	public Result getResult() {
		return result;
	}

	private boolean isDoPost(){
		return getRequest().getMethod().toUpperCase().equals("POST");
	}
	
	public final String execute()throws Exception{
		String result=null;
		if(isDoPost()){
			result = doPost().getResult();
		}else{
			result = doGet().getResult();
		}
		return result;
	}

	public Map<String,String> doPostValidate(){
		return null;
	}
	
	public Map<String,String> doGetValidate(){
		return null;
	}
	
	public Result string(String s){
		result = new StringResultExecute(s);
		return result;
	}
	
	public Result success(){
		result = new SuccessResultExecute();
		return result;
	}
	
	public Result redirect(String url)throws Exception{
		if(url==null)
			throw new NullPointerException("redirect 参数不能为空");
		
		result = new RedirectResultExecute(url);
		return result;
	}
	
	public Result forward(String path) throws Exception{
		if(path==null)
			throw new NullPointerException("forward 参数不能为空");
		if(path!=null && path.endsWith(".ftl")){
			return forwardFTL(path);
		}else {
			return forwardJSP(path);
		}
	}
	
	private Result forwardFTL(String path) throws Exception{
		result = new ForwardFTLResultExecute(path);
		return result;
	}
	
	private Result forwardJSP(String path) throws Exception{
		result = new ForwardJSPResultExecute(path);
		return result;
	}
	
	public Result stream(ByteArrayOutputStream out, String name) {
		result = new StreamResultExecute(out, name);
		return result;
	}
	
}
