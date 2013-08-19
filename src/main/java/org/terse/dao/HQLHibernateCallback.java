package org.terse.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class HQLHibernateCallback implements HibernateCallback {

	/**
	 * 必须的参数，用与SQL查询，在构�?函数中初始化
	 */
	private String queryHQL;
	/**
	 * <CODE><FONT COLOR=RED>RETURNTYPE_LIST_OBJECT</FONT></CODE>或�?<CODE><FONT COLOR=RED>RETURNTYPE_ONE_OBJECT</FONT></CODE><br>
	 * 
	 * <CODE><FONT COLOR=RED>RETURNTYPE_LIST_OBJECT = 0 </FONT></CODE>查询返回LIST对象，默认�?<br>
	 * 
	 * <CODE><FONT COLOR=RED>RETURNTYPE_ONE_OBJECT = 1</FONT></CODE>查询返回�?��对象，无结果返回<FONT COLOR=RED>null</FONT></CODE><br>
	 */
	private int type = 0;
	/**
	 * 执行SQL要用到的参数数组
	 */
	private Object[] parameters = null;

	/**
	 * <CODE><FONT COLOR=RED>RETURNTYPE_LIST_OBJECT = 0 </FONT></CODE>查询返回LIST对象<br>
	 */
	public static final int RETURNTYPE_LIST_OBJECT = 0;
	/**
	 * <CODE><FONT COLOR=RED>RETURNTYPE_ONE_OBJECT = 1</FONT></CODE>查询返回�?��对象，无结果返回<FONT COLOR=RED>null</FONT></CODE><br>
	 */
	public static final int RETURNTYPE_ONE_OBJECT = 1;
	
	public static final int EXECUTE_HQL=2;
	
	private int maxResults=0;
	
	private int firstResult=0;
	
	public HQLHibernateCallback(String queryHQL, Object[] parameters,
			int type) {
		super();
		this.queryHQL = queryHQL;
		this.type = type;
		this.parameters = parameters;
	}
	
	public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
		Object obj = null;
		try {
			Query query = session.createQuery(queryHQL);
			if (parameters != null && parameters.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					query.setParameter(i, parameters[i]);
				}
			}
			long timestart = System.currentTimeMillis();
			switch (type) {
				case RETURNTYPE_LIST_OBJECT: {
					if(maxResults>0&&firstResult>=0){
						obj = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
					}else{
						obj = query.list();
					}
					break;
				}
				case RETURNTYPE_ONE_OBJECT: {
					obj = query.uniqueResult();
					break;
				}
				case EXECUTE_HQL:{
					obj=query.executeUpdate();
					break;
				}
			}
			timestart = System.currentTimeMillis() - timestart;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException();
		} finally {
			session.close();
		}
		return obj;

	}
	
	public HQLHibernateCallback setMaxResults(int maxResults){
		this.maxResults=maxResults;
		return this;
	}
	public HQLHibernateCallback setFirstResult(int firstResult){
		this.firstResult=firstResult;
		return this;
	}
	
}