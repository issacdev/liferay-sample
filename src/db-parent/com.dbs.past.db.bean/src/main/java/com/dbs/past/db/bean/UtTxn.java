package com.dbs.past.db.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.PrimaryKey;
import com.dbs.db.dao.annotation.TableName;
import com.dbs.db.dao.annotation.UIKey;
import com.dbs.past.db.bean.constants.UtTxnConstant;

@TableName("UT_TXN")
public class UtTxn {

	@PrimaryKey
	@UIKey(UtTxnConstant.BRANCH_ID)
	@DatabaseField("BRANCH_ID")
	private String branchId = null;
	
	@PrimaryKey
	@UIKey(UtTxnConstant.REF)
	@DatabaseField("REF")
	private String ref = null;
	
	@UIKey(UtTxnConstant.BRANCH_COM)
	@DatabaseField("BRANCH_COM")
	private BigDecimal branchCom = null;
	
	@UIKey(UtTxnConstant.STATUS)
	@DatabaseField("STATUS")
	private String status = null;
	
	@UIKey(UtTxnConstant.SELECT1)
	@DatabaseField("SELECT1")
	private Boolean select1 = false;
	
	@UIKey(UtTxnConstant.SELECT2)
	@DatabaseField("SELECT2")
	private Boolean select2 = false;
	
	@UIKey(UtTxnConstant.TYPE)
	@DatabaseField("TYPE")
	private String type = null;
	
	@UIKey(UtTxnConstant.FILE_DATE)
	@DatabaseField("FILE_DATE")
	private Date fileDate = null;
	
	@UIKey(UtTxnConstant.DEAL_DATE)
	@DatabaseField("DEAL_DATE")
	private Date dealDate = null;
	
	@UIKey(UtTxnConstant.HOUSE)
	@DatabaseField("HOUSE")
	private String house = null;
	
	@UIKey(UtTxnConstant.FUND)
	@DatabaseField("FUND")
	private String fund = null;
	
	@UIKey(UtTxnConstant.F_NAME)
	@DatabaseField("F_NAME")
	private String fName = null;
	
	@UIKey(UtTxnConstant.F_CCY)
	@DatabaseField("F_CCY")
	private String fCcy = null;

	@UIKey(UtTxnConstant.F_HSE_NET)
	@DatabaseField("F_HSE_NET")
	private BigDecimal fHseNet = null;
	
	@UIKey(UtTxnConstant.F_HSE_COM)
	@DatabaseField("F_HSE_COM")
	private BigDecimal fHseCom = null;

	@UIKey(UtTxnConstant.F_HSE_COM_HKD)
	@DatabaseField("F_HSE_COM_HKD")
	private BigDecimal fHseComHkd = null;
	
	@UIKey(UtTxnConstant.DIS_RATE)
	@DatabaseField("DIS_RATE")
	private BigDecimal disRate = null;
	
	@UIKey(UtTxnConstant.TOTAL_AMT)
	@DatabaseField("TOTAL_AMT")
	private BigDecimal totalAmt = null;
	
	@UIKey(UtTxnConstant.SETTLE_TYPE)
	@DatabaseField("SETTLE_TYPE")
	private String settleType = null;
	
	@UIKey(UtTxnConstant.SETTLE_INSTR)
	@DatabaseField("SETTLE_INSTR")
	private Integer settleInstr = 0;
	
	@UIKey(UtTxnConstant.SETTLE_DATE)
	@DatabaseField("SETTLE_DATE")
	private Date settleDate = null;
	
	@UIKey(UtTxnConstant.HSE_DEAL_REF)
	@DatabaseField("HSE_DEAL_REF")
	private String hseDealRef = null;
	
	@UIKey(UtTxnConstant.PB_CONNOM_COM)
	@DatabaseField("PB_CONNOM_COM")
	private BigDecimal pbConnomCom = null;
	
	@UIKey(UtTxnConstant.PB_COM)
	@DatabaseField("PB_COM")
	private BigDecimal pbCom = null;
	
	@UIKey(UtTxnConstant.PB_AMT)
	@DatabaseField("PB_AMT")
	private BigDecimal pmAmt = null;
	
	@UIKey(UtTxnConstant.CB_COM)
	@DatabaseField("CB_COM")
	private BigDecimal cbCom = null;
	
	@UIKey(UtTxnConstant.CB_AMT)
	@DatabaseField("CB_AMT")
	private BigDecimal cbAmt = null;
	
	@UIKey(UtTxnConstant.CB_AMT)
	@DatabaseField("RECORD_STATUS")
	private String recordStatus = null;
	
	@UIKey(UtTxnConstant.UPLOADED_BY)
	@DatabaseField("UPLOADED_BY")
	private String uploadedBy = null;
	
	@UIKey(UtTxnConstant.UPLOADED_TIME)
	@DatabaseField("UPLOADED_TIME")
	private Date uploadedTime = null;
	
	@UIKey(UtTxnConstant.MODIFIED_BY)
	@DatabaseField("MODIFIED_BY")
	private String modifiedBy = null;
	
	@UIKey(UtTxnConstant.MODIFIED_TIME)
	@DatabaseField("MODIFIED_TIME")
	private Date modifiedTime = null;
	
	@UIKey(UtTxnConstant.CHECKED_BY)
	@DatabaseField("CHECKED_BY")
	private String checked = null;
	
	@UIKey(UtTxnConstant.CHECKED_TIME)
	@DatabaseField("CHECKED_TIME")
	private Date checkedTime = null;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public BigDecimal getBranchCom() {
		return branchCom;
	}

	public void setBranchCom(BigDecimal branchCom) {
		this.branchCom = branchCom;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getSelect1() {
		return select1;
	}

	public void setSelect1(Boolean select1) {
		this.select1 = select1;
	}

	public Boolean getSelect2() {
		return select2;
	}

	public void setSelect2(Boolean select2) {
		this.select2 = select2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getFileDate() {
		return fileDate;
	}

	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getfCcy() {
		return fCcy;
	}

	public void setfCcy(String fCcy) {
		this.fCcy = fCcy;
	}

	public BigDecimal getfHseNet() {
		return fHseNet;
	}

	public void setfHseNet(BigDecimal fHseNet) {
		this.fHseNet = fHseNet;
	}

	public BigDecimal getfHseCom() {
		return fHseCom;
	}

	public void setfHseCom(BigDecimal fHseCom) {
		this.fHseCom = fHseCom;
	}

	public BigDecimal getfHseComHkd() {
		return fHseComHkd;
	}

	public void setfHseComHkd(BigDecimal fHseComHkd) {
		this.fHseComHkd = fHseComHkd;
	}

	public BigDecimal getDisRate() {
		return disRate;
	}

	public void setDisRate(BigDecimal disRate) {
		this.disRate = disRate;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public int getSettleInstr() {
		return settleInstr;
	}

	public void setSettleInstr(Integer settleInstr) {
		this.settleInstr = settleInstr;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public String getHseDealRef() {
		return hseDealRef;
	}

	public void setHseDealRef(String hseDealRef) {
		this.hseDealRef = hseDealRef;
	}

	public BigDecimal getPbConnomCom() {
		return pbConnomCom;
	}

	public void setPbConnomCom(BigDecimal pbConnomCom) {
		this.pbConnomCom = pbConnomCom;
	}

	public BigDecimal getPbCom() {
		return pbCom;
	}

	public void setPbCom(BigDecimal pbCom) {
		this.pbCom = pbCom;
	}

	public BigDecimal getPmAmt() {
		return pmAmt;
	}

	public void setPmAmt(BigDecimal pmAmt) {
		this.pmAmt = pmAmt;
	}

	public BigDecimal getCbCom() {
		return cbCom;
	}

	public void setCbCom(BigDecimal cbCom) {
		this.cbCom = cbCom;
	}

	public BigDecimal getCbAmt() {
		return cbAmt;
	}

	public void setCbAmt(BigDecimal cbAmt) {
		this.cbAmt = cbAmt;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getUploadedTime() {
		return uploadedTime;
	}

	public void setUploadedTime(Date uploadedTime) {
		this.uploadedTime = uploadedTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Date getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(Date checkedTime) {
		this.checkedTime = checkedTime;
	}
	
	
}
