package org.terse.struts;


import org.terse.dao.BaseDao;

public abstract class BaseService {
	
	protected BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
}
