package com.dbs.past.db.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbs.db.ConnectionManager;
import com.dbs.db.dao.DBtoObject;
import com.dbs.db.dao.GenericDao;
import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.dao.IUserConfigDao;

public class UserConfigDaoImpl extends GenericDao<UserConfig> implements IUserConfigDao{

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List<UserConfig> getUserConfigList(String userGroup,
			String userRole, String dataType) {

		String sql = "select * from user_config where user_group = ? and user_role = ? and data_type = ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().prepareStatement(sql);
			stat.setObject(1, userGroup);
			stat.setObject(2, userRole);
			stat.setObject(3, dataType);
			rs = stat.executeQuery();
			return new DBtoObject<UserConfig>().mapObject(rs, ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
		}catch(Exception e){
			logger.error("Error on retrieve list", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return null;
		
	}

	@Override
	public List<UserConfig> getUserConfigList(String userGroup,
			String userRole) {

		String sql = "select * from user_config where user_group = ? and user_role = ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().prepareStatement(sql);
			stat.setObject(1, userGroup);
			stat.setObject(2, userRole);
			rs = stat.executeQuery();
			return new DBtoObject<UserConfig>().mapObject(rs, ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
		}catch(Exception e){
			logger.error("Error on retrieve list", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return null;
		
	}

}
