package com.dbs.past.db.service.impl;

import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.past.db.bean.UploadConfig;
import com.dbs.past.db.dao.IUploadConfigDao;
import com.dbs.past.db.dao.impl.UploadConfigDaoImpl;
import com.dbs.past.db.service.IUploadConfigService;

public class UploadConfigServiceImpl extends ServiceImpl<UploadConfig> implements IUploadConfigService {

	private IUploadConfigDao dao;
	
	@Override
	protected IGenericDao<UploadConfig> getDao() {
		return dao;
	}

	@Override
	public List<UploadConfig> getUploadConfigList(String fileId, String fileType) {
		return dao.getUploadConfigList(fileId, fileType);
	}
	
	public void setDao(IUploadConfigDao dao) {
		this.dao = dao;
	}
}
