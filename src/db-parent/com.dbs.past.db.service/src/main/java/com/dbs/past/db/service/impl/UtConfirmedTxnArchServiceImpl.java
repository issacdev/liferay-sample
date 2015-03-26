package com.dbs.past.db.service.impl;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.past.db.bean.UtConfirmedTxnArch;
import com.dbs.past.db.dao.IUtConfirmedTxnArchDao;
import com.dbs.past.db.service.IUtConfirmedTxnArchService;

public class UtConfirmedTxnArchServiceImpl extends ServiceImpl<UtConfirmedTxnArch> implements IUtConfirmedTxnArchService {

	private IUtConfirmedTxnArchDao dao;

	public void setDao(IUtConfirmedTxnArchDao dao) {
		this.dao = dao;
	}

	@Override
	protected IGenericDao<UtConfirmedTxnArch> getDao() {
		return dao;
	}
}
