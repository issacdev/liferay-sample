package com.dbs.past.db.dao;

import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.past.db.bean.UtApprovalHistory;

public interface IUtApprovalHistoryDao extends IGenericDao<UtApprovalHistory> {

	List<UtApprovalHistory> getApprovalHistoryList(String branchId, String actionType);
}
