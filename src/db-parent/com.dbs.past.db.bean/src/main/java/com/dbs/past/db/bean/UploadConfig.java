package com.dbs.past.db.bean;

import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.PrimaryKey;
import com.dbs.db.dao.annotation.TableName;

@TableName("UPLOAD_CONFIG")
public class UploadConfig {

	@PrimaryKey
	@DatabaseField("FILE_ID")
	private String fileId = null;
	
	@PrimaryKey
	@DatabaseField("FILE_TYPE")
	private String fileType = null;
	
	@DatabaseField("ETL_PATH")
	private String etlPath = null;
	
	@DatabaseField("APPROVAL_COUNT")
	private int approvalCount = 0;

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

	public String getEtlPath() {
		return etlPath;
	}

	public void setEtlPath(String etlPath) {
		this.etlPath = etlPath;
	}

	public int getApprovalCount() {
		return approvalCount;
	}

	public void setApprovalCount(int approvalCount) {
		this.approvalCount = approvalCount;
	}
	
	
	
}
