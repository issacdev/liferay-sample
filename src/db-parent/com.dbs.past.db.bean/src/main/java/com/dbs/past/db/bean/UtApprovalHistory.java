package com.dbs.past.db.bean;

import java.util.Date;

import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.TableName;

@TableName("UT_APPROVAL_HISTORY")
public class UtApprovalHistory {

	@DatabaseField("BATCH_ID")
	private String batchId = null;
	
	@DatabaseField("ACTION_TYPE")
	private String actionType = null;
	
	@DatabaseField("CREATED_BY")
	private String createdBy = null;
	
	@DatabaseField("CREATED_TIME")
	private Date createdTime = null;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	
}
