package com.dbs.past.db.service;

import java.util.List;
import java.util.Map;

import com.dbs.db.service.IService;
import com.dbs.past.db.bean.UserConfig;


public interface IUserConfigService extends IService<UserConfig>{

	public List<UserConfig> getUserConfigList(String userGroup, String userRole);
	
	public UserConfig getUserConfig(String userGroup, String userRole, String dataType);
	
	public List<Map<String, Object>> getUserConfigMapList(String userGrouop, String userRole);
}
