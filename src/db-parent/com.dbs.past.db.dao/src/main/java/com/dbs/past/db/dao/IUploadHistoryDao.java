package com.dbs.past.db.dao;

import com.dbs.db.dao.IGenericDao;
import com.dbs.past.db.bean.UploadHistory;

public interface IUploadHistoryDao extends IGenericDao<UploadHistory>{

	public boolean updateStatus(String batchId, String status);
	
	public boolean updateApprovalCount(String batchId, int approvalCount);
}
