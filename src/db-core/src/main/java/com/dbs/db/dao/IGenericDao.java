package com.dbs.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.dbs.db.filter.Criteria;

public interface IGenericDao<T> {
	public List<T> getAll();
	
	public boolean create(T bean);
	
	public boolean update(T bean) throws Exception;
	
	public boolean delete(T bean) throws Exception;
	
	public boolean deleteAll();
	
	public List<T> findByCriteria(Criteria criteria);
	
	public void commit() throws SQLException;
	
	public void rollback() throws SQLException;
}
