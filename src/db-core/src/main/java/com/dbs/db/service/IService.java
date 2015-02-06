package com.dbs.db.service;

import java.util.List;
import java.util.Map;

import com.dbs.db.filter.Criteria;

public interface IService<T> {
	public boolean create(T bean);
	public boolean update(T bean) throws Exception;
	public boolean delete(T bean) throws Exception;
	public List<T> getAll();
	public List<T> findByCriteria(Criteria criteria);
	public List<Map<String, Object>> beanToMap(List<T> bean);
	public T mapToBean(Map<String, Object> beanMap, boolean create);
}
