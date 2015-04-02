package com.dbs.past.db.dao;

import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.past.db.bean.UserConfig;

public interface IUserConfigDao extends IGenericDao<UserConfig>{

	public List<UserConfig> getUserConfigList(String userGroup, String userRole, String dataType);
	
	public List<UserConfig> getUserConfigList(String userGroup, String userRole);
}
