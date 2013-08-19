package org.terse.hibernate;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import org.terse.dao.BaseDao;

@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable{
	public Object getObject(){
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		request.setAttribute(this.toString(), this);
		return this;
	}
	
	protected BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	
}
