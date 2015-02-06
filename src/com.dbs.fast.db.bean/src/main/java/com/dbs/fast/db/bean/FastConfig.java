package com.dbs.fast.db.bean;

import java.util.Date;

import com.dbs.db.dao.annotation.Audit;
import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.PrimaryKey;
import com.dbs.db.dao.annotation.Sequence;
import com.dbs.db.dao.annotation.TableName;
import com.dbs.db.dao.annotation.UIKey;
import com.dbs.portal.database.constants.FASTConstant;

@TableName("fast_config")
@Audit("fast_config_audit")
public class FastConfig {

	@Sequence("fast_config")
	@PrimaryKey
	@DatabaseField("id")
	@UIKey(FASTConstant.ID)
	private Integer id = null;

	@PrimaryKey
	@DatabaseField("sysCode")
	@UIKey(FASTConstant.SYSCODE)
	private String sysCode = null;

	@DatabaseField("updatedBy")
	@UIKey(FASTConstant.UPDATED_BY)
	private String updatedBy = null;

	@DatabaseField("updatedDateTime")
	@UIKey(FASTConstant.UPDATED_DATE_TIME)
	private Date updatedDateTime = null;

	@PrimaryKey
	@DatabaseField("fkey")
	@UIKey(FASTConstant.FKEY)
	private String fkey = null;

	@DatabaseField("value")
	@UIKey(FASTConstant.VALUE)
	private String value = null;

	@DatabaseField("description")
	@UIKey(FASTConstant.DESCRIPTION)
	private String description = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
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

	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
