package org.terse.dao;

import org.terse.util.Page;

import java.sql.SQLException;
import java.util.List;


public interface BaseDao {

	public <T> void save(List<T> l)throws SQLException;
	
	public void delete(List<Object> l)throws SQLException;

	public <T> T load(Class<T> c, String id) throws SQLException;

	public <T> T get(Class<T> c, String id) throws SQLException;

	public void delete(Object obj) throws SQLException;
	
	public <T> T save(T t) throws SQLException;

	public <T> T update(T t) throws SQLException;

	public <T> T saveOrUpdate(Object obj) throws SQLException;

	public List<Object> findEntity(String hql) throws SQLException;
	
	public <T> List<T> findEntity(String hql, Class<T> clazz) throws SQLException;
	
	public List<Object> findEntity(String hql, Object args) throws SQLException;
	
	public <T> List<T> findEntity(String hql, Object args, Class<T> clazz) throws SQLException;
	
	public List<Object> findEntity(String hql, Object[] args) throws SQLException;
	
	public <T> List<T> findEntity(String hql, Object[] args, Class<T> clazz) throws SQLException;

	public List<Object> findEntity(String hql, int start, int num) throws SQLException;

	public <T> List<T> findEntity(String hql, int start, int num, Class<T> clazz) throws SQLException;
	
	public List<Object> findEntity(final String hql, Object[] args, final int start, final int num) throws SQLException;
	
	public <T> List<T> findEntity(final String hql, Object[] args, final int start, final int num, Class<T> clazz) throws SQLException;
	
	public void executeHQL(String hql) throws SQLException;
	
	public void executeHQL(String hql, Object[] args) throws SQLException;
	
	public void executeSQL(String sql) throws SQLException;

	public void executeSQL(String sql, Object args) throws SQLException;
	
	public void executeSQL(String sql, Object[] args) throws SQLException;

	public List<Object[]> findBySql(final String sql) throws SQLException;

	public List<Object[]> findBySql(final String sql, Object[] args) throws SQLException;
	
	public List<Object[]> findBySql(final String sql, Object args) throws SQLException;
	
	public Object uniqueFindBySql(final String sql) throws SQLException;
	
	public Object uniqueFindBySql(final String sql, Object[] args) throws SQLException;
	
	public Object uniqueFindBySql(final String sql, Object args) throws SQLException;

	public Page search(String hql, int size, int goPage) throws SQLException;
	
	public Page search(String hql, Object[] args, int size, int goPage) throws SQLException ;
	
	public boolean exist(String hql, Object[] args) throws SQLException;

}