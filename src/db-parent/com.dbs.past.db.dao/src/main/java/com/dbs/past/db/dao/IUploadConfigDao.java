package com.dbs.past.db.dao;

import java.util.List;

import com.dbs.db.dao.IGenericDao;
import com.dbs.past.db.bean.UploadConfig;

public interface IUploadConfigDao extends IGenericDao<UploadConfig>{

	List<UploadConfig> getUploadConfigList(String fileId, String fileType);
}
