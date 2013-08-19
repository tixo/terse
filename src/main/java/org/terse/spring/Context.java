package org.terse.spring;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

public abstract class Context extends ContextLoaderListener{
	protected static ApplicationContext ctx;
	
	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}
	
	public static void setCtx(ApplicationContext ctx) {
		Context.ctx = ctx;
	}
}
