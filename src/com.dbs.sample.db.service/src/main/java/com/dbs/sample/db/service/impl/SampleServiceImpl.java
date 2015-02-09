package com.dbs.sample.db.service.impl;

import java.util.List;
import java.util.Map;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.sample.db.bean.Sample;
import com.dbs.sample.db.dao.ISampleDao;
import com.dbs.sample.db.dao.impl.SampleDaoImpl;
import com.dbs.sample.db.service.ISampleService;

public class SampleServiceImpl extends ServiceImpl<Sample> implements ISampleService {

	private ISampleDao dao = new SampleDaoImpl();

	@Override
	protected IGenericDao<Sample> getDao() {
		return dao;
	}

	public List<Map<String, Object>> getSampleList() {
		List<Sample> list = getAll();
		return this.beanToMap(list);
	}
}
