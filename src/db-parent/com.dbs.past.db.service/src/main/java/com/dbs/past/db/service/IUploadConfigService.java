package com.dbs.past.db.service;

import java.util.List;

import com.dbs.db.service.IService;
import com.dbs.past.db.bean.UploadConfig;

public interface IUploadConfigService extends IService<UploadConfig> {
	
	List<UploadConfig> getUploadConfigList(String fileId, String fileType);
}
