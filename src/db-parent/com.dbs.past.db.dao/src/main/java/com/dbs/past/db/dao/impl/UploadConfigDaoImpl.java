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
import com.dbs.past.db.bean.UploadConfig;
import com.dbs.past.db.dao.IUploadConfigDao;

public class UploadConfigDaoImpl extends GenericDao<UploadConfig> implements IUploadConfigDao{

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List<UploadConfig> getUploadConfigList(String fileId,
			String fileType) {
		
		String sql = "select * from upload_config where file_id = ? and file_type = ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().prepareStatement(sql);
			stat.setObject(1, fileId);
			stat.setObject(2, fileType);
			rs = stat.executeQuery();
			return new DBtoObject<UploadConfig>().mapObject(rs, ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
		}catch(Exception e){
			logger.error("Error on retrieve list", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return null;
	}
}
