package com.dbs.past.db.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.PrimaryKey;
import com.dbs.db.dao.annotation.TableName;

@TableName("UT_CONFIRMED_TXN")
public class UtConfirmedTxn {

	@PrimaryKey
	@DatabaseField("BRANCH_ID")
	private String branchId = null;
	
	@PrimaryKey
	@DatabaseField("REF")
	private String ref = null;
	
	@DatabaseField("BRANCH_COM")
	private BigDecimal branchCom = null;
	
	@DatabaseField("STATUS")
	private String status = null;
	
	@DatabaseField("SELECT1")
	private Boolean select1 = false;
	
	@DatabaseField("SELECT2")
	private Boolean select2 = false;
	
	@DatabaseField("TYPE")
	private String type = null;
	
	@DatabaseField("FILE_DATE")
	private Date fileDate = null;
	
	@DatabaseField("DEAL_DATE")
	private Date dealDate = null;
	
	@DatabaseField("HOUSE")
	private String house = null;
	
	@DatabaseField("FUND")
	private String fund = null;
	
	@DatabaseField("F_NAME")
	private String fName = null;
	
	@DatabaseField("F_CCY")
	private String fCcy = null;

	@DatabaseField("F_HSE_NET")
	private BigDecimal fHseNet = null;
	
	@DatabaseField("F_HSE_COM")
	private BigDecimal fHseCom = null;

	@DatabaseField("F_HSE_COM_HKD")
	private BigDecimal fHseComHkd = null;
	
	@DatabaseField("DIS_RATE")
	private BigDecimal disRate = null;
	
	@DatabaseField("TOTAL_AMT")
	private BigDecimal totalAmt = null;
	
	@DatabaseField("SETTLE_TYPE")
	private String settleType = null;
	
	@DatabaseField("SETTLE_INSTR")
	private int settleInstr = 0;
	
	@DatabaseField("SETTLE_DATE")
	private Date settleDate = null;
	
	@DatabaseField("HSE_DEAL_REF")
	private String hseDealRef = null;
	
	@DatabaseField("PB_CONNOM_COM")
	private BigDecimal pbConnomCom = null;
	
	@DatabaseField("PB_COM")
	private BigDecimal pbCom = null;
	
	@DatabaseField("PM_AMT")
	private BigDecimal pmAmt = null;
	
	@DatabaseField("CB_COM")
	private BigDecimal cbCom = null;
	
	@DatabaseField("CB_AMT")
	private BigDecimal cbAmt = null;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
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

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
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

	public void setSettleInstr(int settleInstr) {
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
}
