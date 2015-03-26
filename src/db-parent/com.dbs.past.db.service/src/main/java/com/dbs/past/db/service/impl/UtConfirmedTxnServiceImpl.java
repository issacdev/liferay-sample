package com.dbs.past.db.service.impl;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.past.db.bean.UtConfirmedTxn;
import com.dbs.past.db.dao.IUtConfirmedTxnDao;
import com.dbs.past.db.service.IUtConfirmedTxnService;

public class UtConfirmedTxnServiceImpl extends ServiceImpl<UtConfirmedTxn> implements IUtConfirmedTxnService{

	private IUtConfirmedTxnDao dao;
	
	@Override
	protected IGenericDao<UtConfirmedTxn> getDao() {
		return dao;
	}

	public void setDao(IUtConfirmedTxnDao dao) {
		this.dao = dao;
	}
}
