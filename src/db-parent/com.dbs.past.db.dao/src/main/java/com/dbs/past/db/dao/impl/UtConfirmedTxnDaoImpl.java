package com.dbs.past.db.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbs.db.ConnectionManager;
import com.dbs.db.dao.DBtoObject;
import com.dbs.db.dao.GenericDao;
import com.dbs.past.db.bean.UtConfirmedTxn;
import com.dbs.past.db.dao.IUtConfirmedTxnDao;

public class UtConfirmedTxnDaoImpl extends GenericDao<UtConfirmedTxn> implements IUtConfirmedTxnDao{

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List<UtConfirmedTxn> getUtTxn(String branchId) {

		String sql = "select * from ut_confirmed_txn where branch_id = ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().prepareStatement(sql);
			stat.setObject(1, branchId);
			rs = stat.executeQuery();
			return new DBtoObject<UtConfirmedTxn>().mapObject(rs, ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
		}catch(Exception e){
			logger.error("Error on retrieve list", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return null;
		
	}
}
