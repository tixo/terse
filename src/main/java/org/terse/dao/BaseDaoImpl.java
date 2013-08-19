package org.terse.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.terse.util.Page;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao{
	
	@Override
	public void delete(List<Object> l) throws SQLException {
		getHibernateTemplate().deleteAll(l);
	}
	
	@Override
	public <T> void save(List<T> l)throws SQLException{
		getHibernateTemplate().saveOrUpdateAll(l);
	}

	@Override
	public <T> T  get(Class<T> c, String k) throws SQLException {
		return (T) this.getHibernateTemplate().get(c, k);
	}
	
	@Override
	public <T> T  load(Class<T> c,String k) throws SQLException {
		return (T) this.getHibernateTemplate().load(c, k);
	}

	@Override
	public<T> T save(T t) throws SQLException {
		return (T)this.getHibernateTemplate().save(t);
	}
	
	@Override
	public <T> T update(T t) throws SQLException {
		this.getHibernateTemplate().update(t);
		return t;
	}
	
	@Override
	public Object saveOrUpdate(Object t) throws SQLException {
		this.getHibernateTemplate().saveOrUpdate(t);
		return t;
	}
	
	public Object uniqueFindBySql(String sql, Object args) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,new Object[]{args},SQLHibernateCallback.RETURNTYPE_ONE_OBJECT); 
		return  getHibernateTemplate().execute(sqlhc);
	}
	
	public Object uniqueFindBySql(String sql, Object[] args) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,args,SQLHibernateCallback.RETURNTYPE_ONE_OBJECT); 
		return  getHibernateTemplate().execute(sqlhc);
	}
	
	public Object uniqueFindBySql(String sql) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,null,SQLHibernateCallback.RETURNTYPE_ONE_OBJECT); 
		return  getHibernateTemplate().execute(sqlhc);
	}
	
	@Override
	public void delete(Object e) throws SQLException {
//		Method method=null;
//		try {
//			method=e.getClass().getMethod("setDelete", Boolean.TYPE);
//		} catch (Exception e1) {
//		}
//		if(method!=null){
//			try {
//				method.invoke(e, true);
//				update(e);
//			} catch (Exception e1) {
//				throw new RuntimeException("检查"+e.getClass().getName()+"的"+method.getName()+"方法参数类型是否为boolean以及方法是否为public");
//			} 
//		}else{
			this.getHibernateTemplate().delete(e);
//		}
	}
	@Override
	public List<Object> findEntity(String hql) throws SQLException {
		return this.getHibernateTemplate().find(hql);
	}
	@Override
	public List<Object> findEntity(String hql, Object obj) throws SQLException {
		return this.getHibernateTemplate().find(hql, obj);
	}
	@Override
	public List<Object> findEntity(String hql, Object[] obj) throws SQLException {
		HQLHibernateCallback sqlhc=new HQLHibernateCallback(hql,obj,SQLHibernateCallback.RETURNTYPE_LIST_OBJECT);
		return (List<Object>)getHibernateTemplate().execute(sqlhc);
	}
	@Override
	public List<Object> findEntity(final String hql, final int start, final int num)
			throws SQLException {
		HQLHibernateCallback sqlhc = new HQLHibernateCallback(hql, null,
				SQLHibernateCallback.RETURNTYPE_LIST_OBJECT);
		sqlhc.setFirstResult(start).setMaxResults(num);
		return (List<Object>) getHibernateTemplate().execute(sqlhc);

	}
	@Override
	public List<Object> findEntity(final String hql,Object[] args, final int start, final int num) throws SQLException {
			HQLHibernateCallback sqlhc=new HQLHibernateCallback(hql,args,SQLHibernateCallback.RETURNTYPE_LIST_OBJECT);
			sqlhc.setFirstResult(start).setMaxResults(num);
			return (List<Object>)getHibernateTemplate().execute(sqlhc);
	}
		@Override
	public <T> List<T> findEntity(String hql, Class<T> clazz)
			throws SQLException {
		return (List<T>)findEntity(hql);
	}

	@Override
	public <T> List<T> findEntity(String hql, Object args,
			Class<T> clazz) throws SQLException {
		return (List<T>)findEntity(hql,args);
	}

	@Override
	public <T> List<T> findEntity(String hql, Object[] args,
			Class<T> clazz) throws SQLException {
		return (List<T>)findEntity(hql,args);
	}

	@Override
	public <T> List<T> findEntity(String hql, int start,
			int num, Class<T> clazz) throws SQLException {
		return (List<T>)findEntity(hql,start,num);
	}

	@Override
	public <T> List<T> findEntity(String hql, Object[] args,
			int start, int num, Class<T> clazz) throws SQLException {
		return (List<T>)findEntity(hql,args,start,num);
	}

	@Override
	public boolean exist(String hql, Object[] args) throws SQLException {
		
		List<Object> objs = findEntity(hql,args,0,1);
		return (objs != null && objs.size() > 0) ? true : false;
	}
	@Override
	public Page search(String hql, int size, int goPage) throws SQLException {
		return this.searchForPager(hql,null, size, goPage);
	}
	
	@Override
	public Page search(String hql, Object[] args,int size, int goPage) throws SQLException {
		return this.searchForPager(hql,args,size, goPage);
	}

	private Page searchForPager(String hql,Object[] args,int pageLength, int currentPage) throws SQLException {
		int maxPage;
		int nextPage;
		int prePage;
		List<Object> results;
		List<Integer> pageNum = new ArrayList<Integer>();
		String thql = null;
		if (hql.indexOf("order by") == -1) {
			thql = "select count(*) " + hql;
		} else {
			thql = "select count(*) " + hql.substring(0, hql.indexOf("order by"));
		}
		int count = Integer.valueOf(this.findEntity(thql,args).get(0).toString());
		if (count % pageLength == 0) {
			maxPage = count / pageLength;
		} else {
			maxPage = count / pageLength + 1;
		}
		if (maxPage == 0) {
			maxPage = 1;
			currentPage = 1;
		} else {
			currentPage = currentPage <= maxPage ? (currentPage > 0 ? currentPage : 1) : maxPage;
		}
		if (currentPage < 6) {
			for (int i = 1; i < 10; i++) {
				if (i <= maxPage) {
					pageNum.add(i);
				} else {
					break;
				}
			}
		} else {
			for (int i = currentPage - 4; i <= currentPage + 4; i++) {
				if (i <= maxPage) {
					pageNum.add(i);
				} else {
					break;
				}
			}
		}
		
		if(currentPage+1>maxPage){
			nextPage=maxPage;
		}else{
			nextPage=currentPage+1;
		}
		
		if(currentPage-1<1){
			prePage=1;
		}else{
			prePage=currentPage-1;
		}
		results = findEntity(hql, args,(currentPage - 1) * pageLength, pageLength);
		Page p = new Page(count,currentPage, maxPage, pageNum, results,nextPage,prePage);
		return p;
	}

	/**
	 * 执行hql语句
	 * @param hql
	 * @throws java.sql.SQLException
	 */
	public void executeHQL(String hql) throws SQLException {
		HQLHibernateCallback hqlhc=new HQLHibernateCallback(hql,null,HQLHibernateCallback.EXECUTE_HQL); 
		this.getHibernateTemplate().execute(hqlhc);
	}
	
	public void executeHQL(String hql,Object[] args) throws SQLException {
		HQLHibernateCallback hqlhc=new HQLHibernateCallback(hql,args,HQLHibernateCallback.EXECUTE_HQL); 
		this.getHibernateTemplate().execute(hqlhc);
	}
	
	@Override
	public void executeSQL(String sql) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,null,SQLHibernateCallback.EXECUTE_SQL); 
		this.getHibernateTemplate().execute(sqlhc);
	}
	
	@Override
	public void executeSQL(String sql, Object args) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,new Object[]{args},SQLHibernateCallback.EXECUTE_SQL); 
		this.getHibernateTemplate().execute(sqlhc);
	}

	@Override
	public void executeSQL(String sql, Object[] args) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,args,SQLHibernateCallback.EXECUTE_SQL); 
		this.getHibernateTemplate().execute(sqlhc);
	}

	@Override
	public List<Object[]> findBySql(String sql) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,null,SQLHibernateCallback.RETURNTYPE_LIST_OBJECT); 
		return  (List<Object[]>) getHibernateTemplate().execute(sqlhc);
	}

	@Override
	public List<Object[]> findBySql(String sql, Object[] args) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,args,SQLHibernateCallback.RETURNTYPE_LIST_OBJECT); 
		return  (List<Object[]>) getHibernateTemplate().execute(sqlhc);
	}

	@Override
	public List<Object[]> findBySql(String sql, Object args) throws SQLException {
		SQLHibernateCallback sqlhc=new SQLHibernateCallback(sql,new Object[]{args},SQLHibernateCallback.RETURNTYPE_LIST_OBJECT); 
		return  (List<Object[]>) getHibernateTemplate().execute(sqlhc);
	}


}
