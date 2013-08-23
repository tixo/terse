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
			//String ftl="/WEB-INF/classes/"+ReflectUtil.namespaceToMobileFtl(invocation.getAction().getClass().getName());
			//TODO 此处待测试����
			//��namespaceToMobileFtl������namespaceToAllPath
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
		//TODO 此处待测试�����
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		String client = request.getParameter("xinlong_client");
		if(null == client || "".equals(client)) {
			return SUCCESS;
		} else {
			return T_RESULT;
		}
	}
	
}
