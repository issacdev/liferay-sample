package com.dbs.past.db.service;

import java.util.List;

import com.dbs.db.service.IService;
import com.dbs.past.db.bean.UtTxnDoaHistory;

public interface IUtTxnDoaHistoryService extends IService<UtTxnDoaHistory> {

	public List<UtTxnDoaHistory> getDoaList(String dataType);  
}
