package com.dbs.past.db.bean;

import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.TableName;
import com.dbs.db.dao.annotation.UIKey;
import com.dbs.past.db.bean.constants.UserConfigConstant;

@TableName("USER_CONFIG")
public class UserConfig {

	@UIKey(UserConfigConstant.USER_GROUP)
	@DatabaseField("USER_GROUP")
	private String userGroup;
	
	@DatabaseField("USER_ROLE")
	private String userRole;
	
	@UIKey(UserConfigConstant.DATA_TYPE)
	@DatabaseField("DATA_TYPE")
	private String dataType;
	
	@UIKey(UserConfigConstant.TABLE_VIEW)
	@DatabaseField("TABLE_VIEW")
	private String tableView;
	
	@DatabaseField("ETL_PATH")
	private String etlPath;
	
	@DatabaseField("DOA_ENABLE")
	private Boolean doaEnable;
	
	@DatabaseField("DOA_COUNT")
	private Integer doaCount;
	
	public String getTableView() {
		return tableView;
	}
	public void setTableView(String tableView) {
		this.tableView = tableView;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getEtlPath() {
		return etlPath;
	}
	public void setEtlPath(String etlPath) {
		this.etlPath = etlPath;
	}
	public Boolean getDoaEnable() {
		return doaEnable;
	}
	public void setDoaEnable(Boolean doaEnable) {
		this.doaEnable = doaEnable;
	}
	public Integer getDoaCount() {
		return doaCount;
	}
	public void setDoaCount(Integer doaCount) {
		this.doaCount = doaCount;
	}
}
