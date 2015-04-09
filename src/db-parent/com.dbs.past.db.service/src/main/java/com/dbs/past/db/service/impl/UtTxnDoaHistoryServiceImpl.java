package com.dbs.past.db.service.impl;

import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.past.db.bean.UtTxnDoaHistory;
import com.dbs.past.db.dao.IUtTxnDoaHistoryDao;
import com.dbs.past.db.service.IUtTxnDoaHistoryService;

public class UtTxnDoaHistoryServiceImpl extends ServiceImpl<UtTxnDoaHistory> implements IUtTxnDoaHistoryService{

	private IUtTxnDoaHistoryDao dao;
	
	@Override
	protected IGenericDao<UtTxnDoaHistory> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public void setDao(IUtTxnDoaHistoryDao dao) {
		this.dao = dao;
	}

	@Override
	public List<UtTxnDoaHistory> getDoaList(String dataType) {
		return dao.getDoaList(dataType);
	}
}
