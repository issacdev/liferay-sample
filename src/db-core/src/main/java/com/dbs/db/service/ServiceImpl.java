package com.dbs.db.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.dao.annotation.UIKey;
import com.dbs.db.filter.Criteria;

public abstract class ServiceImpl<T> implements IService<T> {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public boolean create(T bean) {
		return getDao().create(bean);
	}

	public boolean update(T bean) throws Exception {
		return getDao().update(bean);
	}

	public boolean delete(T bean) throws Exception {
		return getDao().delete(bean);
	}

	public List<T> getAll() {
		return getDao().getAll();
	}

	public List<T> findByCriteria(Criteria criteria) {
		return getDao().findByCriteria(criteria);
	}
	
	protected abstract IGenericDao<T> getDao();
	
	public List<Map<String, Object>> beanToMap(List<T> dataList){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		if (dataList != null){
			for (T bean : dataList){
				
				Field[] fields = bean.getClass().getDeclaredFields();
				Map<String, Object> map = new HashMap<String, Object>();
				for (Field field : fields){
					field.setAccessible(true);
					UIKey uiKey = field.getAnnotation(UIKey.class);
					if (uiKey != null){
						try {
							map.put(uiKey.value(), field.get(bean));
						} catch (IllegalArgumentException e) {
							logger.debug(e.getMessage(), e);
						} catch (IllegalAccessException e) {
							logger.debug(e.getMessage(), e);
						}
					}
				}
				map.put("ACTION", "");
				list.add(map);
			}
		}
		
		return list;
	}

	public T mapToBean(Map<String, Object> beanMap, boolean create) {
		return mapToBean(beanMap);
	}
	
	public T mapToBean(Map<String, Object> beanMap) {
		try{
			String className = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
			Class cls = Class.forName(className);
			
			Object obj = cls.newInstance();
			
			if (beanMap != null){
				Field[] fields = obj.getClass().getDeclaredFields();
				if (fields != null){
					for (Field field : fields){
						field.setAccessible(true);
						UIKey uiKey = field.getAnnotation(UIKey.class);
						if (uiKey != null){
							try{
								field.set(obj, beanMap.get(uiKey.value()));
							}catch(Exception e){
								logger.debug(e.getMessage(), e);
							}
						}
					}
				}
			}
			return (T)obj;
		}catch(Exception e){
			logger.debug(e.getMessage(), e);
		}
		return null;
	}
	
}
