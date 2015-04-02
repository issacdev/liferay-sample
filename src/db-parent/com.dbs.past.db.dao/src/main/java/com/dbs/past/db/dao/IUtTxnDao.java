package com.dbs.past.db.dao;

import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.past.db.bean.UtTxn;

public interface IUtTxnDao extends IGenericDao<UtTxn>{

	public List<UtTxn> getUtTxnByRecordStatus(String status);
}
