package com.dbs.past.db.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dbs.db.dao.IGenericDao;
import com.dbs.db.service.ServiceImpl;
import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.dao.IUserConfigDao;
import com.dbs.past.db.dao.impl.UserConfigDaoImpl;
import com.dbs.past.db.service.IUserConfigService;

public class UserConfigServiceImpl extends ServiceImpl<UserConfig> implements IUserConfigService{

	private IUserConfigDao dao;
	
	@Override
	protected IGenericDao<UserConfig> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public void setDao(IUserConfigDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<UserConfig> getUserConfigList(String userGroup, String userRole) {
		
		return dao.getUserConfigList(userGroup, userRole);	
	}

	@Override
	public List<Map<String, Object>> getUserConfigMapList(String userGroup,
			String userRole) {

		List<UserConfig> list = dao.getUserConfigList(userGroup, userRole);

		return this.beanToMap(list);
	}

	@Override
	public UserConfig getUserConfig(String userGroup,
			String userRole, String dataType) {
		
		List<UserConfig> configList = dao.getUserConfigList(userGroup, userRole, dataType);	
	
		if(configList != null && configList.size() > 0){
			return configList.get(0);
		}
		
		return null;	
	}
}
