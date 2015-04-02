package com.dbs.past.db.service.impl;

import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.past.db.bean.UtTxn;
import com.dbs.past.db.dao.IUtTxnDao;
import com.dbs.past.db.service.IUtTxnService;

public class UtTxnServiceImpl extends ServiceImpl<UtTxn> implements IUtTxnService{

	private IUtTxnDao dao = null;
	
	@Override
	protected IGenericDao<UtTxn> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public void setDao(IUtTxnDao dao) {
		this.dao = dao;
	}

	@Override
	public List<UtTxn> getUtTxnByRecordStatus(String status) {
		return dao.getUtTxnByRecordStatus(status);
	}

	
	
}
