package com.dbs.past.db.dao;

import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.past.db.bean.UtTxnDoaHistory;

public interface IUtTxnDoaHistoryDao extends IGenericDao<UtTxnDoaHistory>{

	public List<UtTxnDoaHistory> getDoaList(String dataType);  
}
