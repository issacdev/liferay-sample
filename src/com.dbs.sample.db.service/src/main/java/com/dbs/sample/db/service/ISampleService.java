package com.dbs.sample.db.service;

import java.util.List;
import java.util.Map;

import com.dbs.db.service.IService;
import com.dbs.sample.db.bean.Sample;

public interface ISampleService extends IService<Sample> {

	public List<Map<String, Object>> getSampleList();

}
