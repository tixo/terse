package org.terse.struts;

import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import org.terse.util.ReflectUtil;

public class TResult extends StrutsResultSupport{

	private static final long serialVersionUID = 1026075033897534605L;
	
	private FreemarkerManager freemarkerManager;
	
	@Inject
    public void setFreemarkerManager(FreemarkerManager mgr) {
        this.freemarkerManager = mgr;
    }

	public FreemarkerManager getFreemarkerManager() {
		return freemarkerManager;
	}

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		ResultExecute result=(ResultExecute) ReflectUtil.invokeGet(invocation.getAction(), "getResult");
		result.doResultExecute(this, invocation);
	}
}
