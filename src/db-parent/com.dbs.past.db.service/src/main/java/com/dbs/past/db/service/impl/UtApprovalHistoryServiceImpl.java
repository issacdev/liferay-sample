package com.dbs.past.db.service.impl;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.past.db.bean.UtApprovalHistory;
import com.dbs.past.db.dao.IUtApprovalHistoryDao;
import com.dbs.past.db.service.IUtApprovalHistoryService;

public class UtApprovalHistoryServiceImpl extends ServiceImpl<UtApprovalHistory> implements IUtApprovalHistoryService {

	private IUtApprovalHistoryDao dao;
	
	@Override
	protected IGenericDao<UtApprovalHistory> getDao() {
		return dao;
	}

	public void setDao(IUtApprovalHistoryDao dao) {
		this.dao = dao;
	}
	
	
}
