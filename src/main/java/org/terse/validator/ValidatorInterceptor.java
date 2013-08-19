package org.terse.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.opensymphony.xwork2.ActionInvocation;
import org.terse.interceptor.BaseInterceptor;
import org.terse.spring.Context;
import org.terse.util.ReflectUtil;

public class ValidatorInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 3764387952778928496L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if(configValidate(invocation) && customerValidate(invocation)){
			return invocation.invoke();
		}
		return "INPUT_ERROR";
	}
	/**
	 * 客户端自定义验证方法
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean customerValidate(ActionInvocation invocation) throws Exception{
		Map<String,String> result = null;
		String methodName = "doPostValidate";
		if(!isPost()){
			methodName = "doGetValidate";
		}
		Method validateMethod = invocation.getAction().getClass().getMethod(methodName);
		result =  (Map<String,String>)validateMethod.invoke(invocation.getAction());
		if(result!=null && result.size()>0){
			Method setErrorMethod =invocation.getAction().getClass().getMethod("setErrorMap",Map.class);
			setErrorMethod.invoke(invocation.getAction(), result);
			return false;
		}
		return true;
	}
	
	/**
	 * 配置文件验证
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	private boolean configValidate(ActionInvocation invocation) throws Exception{
		String filePath = getFilePath(invocation);
		Document document = getValidatorConfig(filePath);
		Map<String,String> result = null;
		if(document!=null){
			result = validator(document);
			if(result!=null && result.size()>0){
				Method setErrorMethod = invocation.getAction().getClass().getMethod("setErrorMap",Map.class);
				setErrorMethod.invoke(invocation.getAction(), result);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param document
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private Map<String,String> validator(Document document) throws Exception {
		Map<String,String> result = new HashMap<String,String>();
		List list = document.getRootElement().elements("parameter");
		for(int i = 0 ;i < list.size();i++){
			Element e = (Element)list.get(i);
			String field = e.attributeValue("name");
			String value = getRequest().getParameter(field);
			boolean required = Boolean.valueOf(e.attributeValue("required"));
			if(required || (!required && value!=null)){
				String validatorId = e.element("validator").attributeValue("id");
				List conditionList = e.element("validator").elements("condition");
				Element messageElement = e.element("validator").element("message");
				String message = messageElement!=null?messageElement.getText():null;
				Map<String,String> conditionMap = new HashMap<String,String>();
				for(int j = 0;j<conditionList.size();j++){
					Element conditionElement = (Element)conditionList.get(j);
					String conditionName = conditionElement.attributeValue("name");
					String conditionValue= conditionElement.attributeValue("value");
					conditionMap.put(conditionName, conditionValue);
				}
				IValidator validator = (IValidator) Context.getBean(validatorId);
				String errorMessage = validator.validate(value, conditionMap,message);
				if(errorMessage != null){
					result.put(field, errorMessage);
				}
			}
		}
		return result;
	}

	/**
	 * 从缓存中取得配置文件
	 * @param filePath
	 * @return
	 * @throws java.io.IOException
	 * @throws DocumentException
	 */
	private Document getValidatorConfig(String filePath) throws IOException, DocumentException{
		Document document = null ;
		//Document document = ValidatorConfig.get(filePath);
		if(document==null){
			File file = new File(filePath);
			if(file.exists() && file.isFile()){
				String str = readXML(file);
				document = DocumentHelper.parseText(str);
			}
		}
		return document;
	}
	
	private boolean isPost(){
		return getRequest().getMethod().toUpperCase().equals("POST");
	}
	
	/**
	 * 获得该Action对应的验证文件
	 * @param invocation
	 * @return
	 */
	private String getFilePath(ActionInvocation invocation){
		StringBuffer filePath = new StringBuffer(getRequest().getSession().getServletContext().getRealPath("/"));
		filePath.append("/WEB-INF/classes/");
		filePath.append(ReflectUtil.namespaceToAllPath(invocation.getAction().getClass().getName()));
		filePath.append("/");
		if(isPost()){
			filePath.append("post-");
		}else{
			filePath.append("get-");
		}
		filePath.append("validate-");
		filePath.append(ReflectUtil.getClassName(invocation.getAction().getClass().getName()));
		filePath.append(".xml");
		return filePath.toString();
	}

	/**
	 * 读取文件中XML文件内容
	 * @param file
	 * @return
	 * @throws java.io.IOException
	 */
	private static String readXML(File file) throws IOException {
		InputStream fis = null; 
		try{
			fis = new FileInputStream(file);
			byte[] b=new byte[fis.available()];
			fis.read(b);
			return new String(b);
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
