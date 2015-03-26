package com.dbs.past.db.service.impl;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.past.db.bean.UploadHistory;
import com.dbs.past.db.dao.IUploadHistoryDao;
import com.dbs.past.db.service.IUploadHistoryService;

public class UploadHistoryServiceImpl extends ServiceImpl<UploadHistory> implements IUploadHistoryService {

	private IUploadHistoryDao dao;
	
	@Override
	protected IGenericDao<UploadHistory> getDao() {
		return dao;
	}
	
	@Override
	public boolean updateStatus(String batchId, String status) {
		return dao.updateStatus(batchId, status);
	}

	@Override
	public boolean updateApprovalCount(String batchId, int approvalCount) {
		return dao.updateApprovalCount(batchId, approvalCount);
	}
	
	public void setDao(IUploadHistoryDao dao) {
		this.dao = dao;
	}
}
