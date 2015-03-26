package com.dbs.past.db.dao;

import java.util.Date;
import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.past.db.bean.UtConfirmedTxn;

public interface IUtConfirmedTxnDao extends IGenericDao<UtConfirmedTxn> {
	
	List<UtConfirmedTxn> getUtTxn(String branchId);
}
