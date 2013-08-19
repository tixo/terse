package org.terse.interceptor;

import javax.servlet.http.Cookie;
import com.opensymphony.xwork2.ActionInvocation;

public class SessionInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -3837052121501078240L;

	@Override
	public String intercept(ActionInvocation action) throws Exception {
		if(getRequest().getMethod().toUpperCase().equals("POST")){
			Cookie[] cookies = getRequest().getCookies();
			String sessionID = null;
			if(cookies!=null){
				for (int i = 0; i < cookies.length; i++) {
					if ("jsessionid".equals(cookies[i].getName())) {
						sessionID = cookies[i].getValue();
						break;
					}
				}
			}
			action.getAction().getClass().getMethod("setSessionID",
					String.class).invoke(action.getAction(), sessionID);
		}
		return action.invoke();
	}

}
