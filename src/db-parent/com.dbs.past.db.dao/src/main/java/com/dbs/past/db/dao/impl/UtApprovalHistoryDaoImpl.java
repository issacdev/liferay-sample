package com.dbs.past.db.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbs.db.ConnectionManager;
import com.dbs.db.dao.DBtoObject;
import com.dbs.db.dao.GenericDao;
import com.dbs.past.db.bean.UtApprovalHistory;
import com.dbs.past.db.dao.IUtApprovalHistoryDao;

public class UtApprovalHistoryDaoImpl extends GenericDao<UtApprovalHistory> implements IUtApprovalHistoryDao{

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List<UtApprovalHistory> getApprovalHistoryList(String batchId,
			String actionType) {
		
		String sql = "select * from ut_approval_history where batch_id = ? and action_type = ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().prepareStatement(sql);
			stat.setObject(1, batchId);
			stat.setObject(2, actionType);
			rs = stat.executeQuery();
			return new DBtoObject<UtApprovalHistory>().mapObject(rs, ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
		}catch(Exception e){
			logger.error("Error on retrieve list", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return null;
	}
}
