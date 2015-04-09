package com.dbs.past.db.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbs.db.ConnectionManager;
import com.dbs.db.dao.DBtoObject;
import com.dbs.db.dao.GenericDao;
import com.dbs.past.db.bean.UtTxn;
import com.dbs.past.db.bean.UtTxnDoaHistory;
import com.dbs.past.db.dao.IUtTxnDoaHistoryDao;

public class UtTxnDoaHistoryDaoImpl extends GenericDao<UtTxnDoaHistory> implements IUtTxnDoaHistoryDao{

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public List<UtTxnDoaHistory> getDoaList(String dataType) {

		String sql = "select * from ut_txn_doa_history where data_type = ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().prepareStatement(sql);
			stat.setObject(1, dataType);
			
			rs = stat.executeQuery();
			return new DBtoObject<UtTxnDoaHistory>().mapObject(rs, ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
		}catch(Exception e){
			logger.error("Error on retrieve list", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return null;
	}
}
