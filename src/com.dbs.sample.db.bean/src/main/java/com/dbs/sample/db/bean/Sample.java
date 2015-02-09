package com.dbs.sample.db.bean;

import java.util.Date;

import com.dbs.db.dao.annotation.Audit;
import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.PrimaryKey;
import com.dbs.db.dao.annotation.Sequence;
import com.dbs.db.dao.annotation.TableName;
import com.dbs.db.dao.annotation.UIKey;
import com.dbs.portal.database.constants.SampleConstant;

@TableName("sample")
@Audit("sample_audit")
public class Sample {

	@Sequence("sample")
	@PrimaryKey
	@DatabaseField("id")
	@UIKey(SampleConstant.ID)
	private Integer id = null;

	@DatabaseField("skey")
	@UIKey(SampleConstant.SKEY)
	private String skey = null;

	@DatabaseField("value")
	@UIKey(SampleConstant.VALUE)
	private String value = null;

	@DatabaseField("updatedBy")
	@UIKey(SampleConstant.UPDATED_BY)
	private String updatedBy = null;

	@DatabaseField("updatedDateTime")
	@UIKey(SampleConstant.UPDATED_DATE_TIME)
	private Date updatedDateTime = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

}
