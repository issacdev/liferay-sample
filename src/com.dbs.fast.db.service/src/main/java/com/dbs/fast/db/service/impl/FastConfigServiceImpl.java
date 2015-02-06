package com.dbs.fast.db.service.impl;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.fast.db.bean.FastConfig;
import com.dbs.fast.db.dao.IFastConfigDao;
import com.dbs.fast.db.dao.impl.FastConfigDaoImpl;
import com.dbs.fast.db.service.IFastConfigService;

public class FastConfigServiceImpl extends ServiceImpl<FastConfig> implements IFastConfigService {

	private IFastConfigDao dao = new FastConfigDaoImpl();

	@Override
	protected IGenericDao<FastConfig> getDao() {
		return dao;
	}

}
