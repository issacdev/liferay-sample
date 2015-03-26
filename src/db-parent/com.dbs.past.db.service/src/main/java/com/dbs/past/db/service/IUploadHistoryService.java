package com.dbs.past.db.service;

import com.dbs.db.service.IService;
import com.dbs.past.db.bean.UploadHistory;

public interface IUploadHistoryService extends IService<UploadHistory> {

	public boolean updateStatus(String batchId, String status);
	
	public boolean updateApprovalCount(String batchId, int approvalCount);
}
