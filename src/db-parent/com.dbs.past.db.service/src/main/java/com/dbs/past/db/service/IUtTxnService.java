package com.dbs.past.db.service;

import java.util.List;

import com.dbs.db.service.IService;
import com.dbs.past.db.bean.UtTxn;

public interface IUtTxnService extends IService<UtTxn>{

	public List<UtTxn> getUtTxnByRecordStatus(String status);
}
