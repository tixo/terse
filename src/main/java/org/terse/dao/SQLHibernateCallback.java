package org.terse.dao;

import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SQLHibernateCallback implements HibernateCallback {
	
	private String querySQL;
	
	private int type = 0;
	
	private Object[] parameters = null;
	
	public static final int RETURNTYPE_LIST_OBJECT = 0;
	
	public static final int RETURNTYPE_ONE_OBJECT = 1;
	
	public static final int EXECUTE_SQL = 2;

	public SQLHibernateCallback(String querySQL, Object[] parameters,int type) {
		super();
		this.querySQL = querySQL;
		this.type = type;
		this.parameters = parameters;
	}

	public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
		Object obj = null;
		try {
			Query query = session.createSQLQuery(querySQL);
			if (parameters != null && parameters.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					query = query.setParameter(i, parameters[i]);
				}
			}
			long timestart = System.currentTimeMillis();
			switch (type) {
			case RETURNTYPE_LIST_OBJECT:
				obj = query.list();	
				break;
			case RETURNTYPE_ONE_OBJECT:
				obj = query.uniqueResult();
				break;
			case EXECUTE_SQL:
				obj = query.executeUpdate();
				break;	
			}
			timestart = System.currentTimeMillis() - timestart;
		} catch (Exception e) {
			throw new SQLException();
		} finally {
			session.close();
		}
		return obj;
	}
}
