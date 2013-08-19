package org.terse.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.terse.freemarker.URL;

public class FreemarkerTagInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -3837052121501078240L;

	@Override
	public String intercept(ActionInvocation action) throws Exception {
		ActionContext ctx = action.getInvocationContext();
		Map<String,Object> map = ctx.getContextMap();
		map.put("URL", new URL());
		return action.invoke();
	}

}
