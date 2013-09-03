package org.terse.struts;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.views.freemarker.FreemarkerResult;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.terse.util.ReflectUtil;

import freemarker.template.TemplateException;

public class SuccessResultExecute extends Result implements ResultExecute{

	@Override
	public void doResultExecute(TResult tResult,ActionInvocation invocation) {
		try {
			String ftl="/WEB-INF/classes/"+ReflectUtil.namespaceToAllPath(invocation.getAction().getClass().getName());
			FreemarkerResult fr=new FreemarkerResult();
			fr.setFreemarkerManager(tResult.getFreemarkerManager());
			fr.doExecute(ftl, invocation);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getResult() {
		return SUCCESS;
	}
	
}
