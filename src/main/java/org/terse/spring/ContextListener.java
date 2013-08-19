package org.terse.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ContextListener extends Context {
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
	}
}