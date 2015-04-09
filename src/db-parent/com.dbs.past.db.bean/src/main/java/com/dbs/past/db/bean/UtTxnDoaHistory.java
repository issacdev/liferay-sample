package com.dbs.past.db.bean;

import java.util.Date;

import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.PrimaryKey;
import com.dbs.db.dao.annotation.TableName;
import com.dbs.db.dao.annotation.UIKey;
import com.dbs.past.db.bean.constants.UtTxnDoaHistoryConstant;

@TableName("UT_TXN_DOA_HISTORY")
public class UtTxnDoaHistory {

	@PrimaryKey
	@DatabaseField("DATA_TYPE")
	private String dataType;
	
	@PrimaryKey
	@DatabaseField("RECORD_DATE")
	private Date recordDate;
	
	@UIKey(UtTxnDoaHistoryConstant.APPROVED_BY)
	@DatabaseField("APPROVED_BY")
	private String approvedBy;
	
	@UIKey(UtTxnDoaHistoryConstant.APPROVED_DATE)
	@DatabaseField("APPROVED_DATE")
	private Date approvedDate;
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
}
