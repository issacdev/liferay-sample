package com.dbs.past.db.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.dbs.db.ConnectionManager;
import com.dbs.db.dao.DBtoObject;
import com.dbs.db.dao.GenericDao;
import com.dbs.past.db.bean.UploadHistory;
import com.dbs.past.db.dao.IUploadHistoryDao;

public class UploadHistoryDaoImpl extends GenericDao<UploadHistory> implements IUploadHistoryDao{

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean updateStatus(String batchId, String status) {

		String sql = "update upload_history set status = ? where batch_id = ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().prepareStatement(sql);
			stat.setObject(1, status);
			stat.setObject(2, batchId);
			
			return stat.executeUpdate() > 0;
			
		}catch(Exception e){
			logger.error("Error on updaing record status", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return false;
	}

	@Override
	public boolean updateApprovalCount(String batchId, int approvalCount) {

		String sql = "update upload_history set approval_count = ? where batch_id = ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().prepareStatement(sql);
			stat.setObject(1, approvalCount);
			stat.setObject(2, batchId);
			
			return stat.executeUpdate() > 0;
			
		}catch(Exception e){
			logger.error("Error on updaing record approval count", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return false;
	}


}
