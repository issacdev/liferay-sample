package com.dbs.past.db.bean;

import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.PrimaryKey;
import com.dbs.db.dao.annotation.TableName;

@TableName("UPLOAD_HISTORY")
public class UploadHistory {

	@PrimaryKey
	@DatabaseField("BATCH_ID")
	private String batchId = null;
	
	@PrimaryKey
	@DatabaseField("FILE_ID")
	private String fileId = null;
	
	@PrimaryKey
	@DatabaseField("FILE_TYPE")
	private String fileType = null;
	
	@DatabaseField("STATUS")
	private String status = null;
	
	@DatabaseField("APPROVAL_COUNT")
	private int approvalCount = 0;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getApprovalCount() {
		return approvalCount;
	}

	public void setApprovalCount(int approvalCount) {
		this.approvalCount = approvalCount;
	}
	
	
}
