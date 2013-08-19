package org.terse.struts;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.views.freemarker.FreemarkerResult;

import com.opensymphony.xwork2.ActionInvocation;
import org.terse.util.ReflectUtil;

import freemarker.template.TemplateException;

public class ForwardFTLResultExecute extends Result implements ResultExecute{
	
	private String ftl;
	
	protected ForwardFTLResultExecute(String ftl){
		this.ftl = ftl;
	}
	
	@Override
	public void doResultExecute(TResult tResult,ActionInvocation invocation) {
    	try {
    		if(ftl!=null && !ftl.startsWith("/")){
				ftl = "/WEB-INF/classes/" + ReflectUtil.namespaceToAllPath(invocation.getAction().getClass().getName()) + "/" + ftl;
    		}
    		FreemarkerResult fr=new FreemarkerResult();
    		fr.setFreemarkerManager(tResult.getFreemarkerManager());
    		fr.doExecute(ftl, invocation);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
