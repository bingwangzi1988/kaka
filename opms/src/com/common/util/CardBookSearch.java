package com.common.util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class CardBookSearch implements Serializable {
    

	/** PK */
	private java.lang.String id;
	/** ���� */
	private java.lang.String cardNo;
	/** ����� */
	private java.lang.String cardSeq;
	/** ��Ʒ�� */
	private java.lang.String cardKind;
	/** ����� */
	private java.lang.String cardType;
	/** �����л��� */
	private java.lang.String openBrc;
	/** ��������Ա */
	private java.lang.String openOpr;
	/** �ƿ�״̬ */
	private java.lang.String mkcardStat;
	/** ��״̬ */
	private java.lang.String cardStat;
	/** ״̬���� */
	private java.lang.String statRemark;
	/** ��֤�� */
	private java.lang.String vertifyNum;
	/** ʧЧ���� */
	private java.util.Date expireDateBegin;
	private java.util.Date expireDateEnd;
	/** ������� */
	private java.lang.String serviceCode;
	/** �ɿ��� */
	private java.lang.String oldCardNo;
	/** �ɿ����к� */
	private java.lang.String oldCardSeq;
	/** �������� */
	private java.util.Date openDateBegin;
	private java.util.Date openDateEnd;
	/** ���ջ����� */
	private java.util.Date backDateBegin;
	private java.util.Date backDateEnd;
	/** �������� */
	private java.util.Date closeDateBegin;
	private java.util.Date closeDateEnd;
	/** �ͻ��� */
	private java.lang.String customNo;
	/** ���֤���� */
	private java.lang.String certType;
	/** ���֤���� */
	private java.lang.String certNo;
	/** �ͻ����� */
	private java.lang.String custName;
	/** �ͻ�Ӣ������ */
	private java.lang.String custEname;
	/** ��Ƭ�ȼ� */
	private java.lang.String custKind;
	/** ƾ֤���� */
	private java.lang.String vouNo;
	/** ��Ʒ���� */
	private java.lang.String cardAcctType;
	/** ������ */
	private java.lang.String mainCardNo;
	/** Ա������־ */
	private java.lang.String empFlg;
	/** ��������־ */
	private java.lang.String cardFlag;
	/** ������ */
	private java.lang.String cardPhySort;
	/** ������־ */
	private java.lang.String nameFlag;
	/** ��Ƭ��־ */
	private java.lang.String photoFlag;
	/** IC����־λ */
	private java.lang.String icFlg;
	/** �շ����� */
	private java.lang.String feeTp;
	/** �������˻���־ */
	private java.lang.String defAcflg;
	/** Ѻ�� */
	private java.math.BigDecimal feeAt;
	/** ���� */
	private java.lang.String memo;
	
	private java.lang.Integer lmtCnt;
	private java.lang.Integer lmtCnts;
	public java.lang.Integer getLmtCnts() {
		return lmtCnts;
	}

	public void setLmtCnts(java.lang.Integer lmtCnts) {
		this.lmtCnts = lmtCnts;
	}

	public java.lang.Integer getLmtCnt() {
		return lmtCnt;
	}

	public void setLmtCnt(java.lang.Integer lmtCnt) {
		this.lmtCnt = lmtCnt;
	}
private java.lang.String lostStat;
private java.util.Date zsLostDate;
private java.util.Date pwLostDate;
private java.util.Date ktLostDate;
private java.util.Date sgLostDate;

	public java.lang.String getLostStat() {
	return lostStat;
}

public void setLostStat(java.lang.String lostStat) {
	this.lostStat = lostStat;
}

public java.util.Date getZsLostDate() {
	return zsLostDate;
}

public void setZsLostDate(java.util.Date zsLostDate) {
	this.zsLostDate = zsLostDate;
}

public java.util.Date getPwLostDate() {
	return pwLostDate;
}

public void setPwLostDate(java.util.Date pwLostDate) {
	this.pwLostDate = pwLostDate;
}

public java.util.Date getKtLostDate() {
	return ktLostDate;
}

public void setKtLostDate(java.util.Date ktLostDate) {
	this.ktLostDate = ktLostDate;
}

public java.util.Date getSgLostDate() {
	return sgLostDate;
}

public void setSgLostDate(java.util.Date sgLostDate) {
	this.sgLostDate = sgLostDate;
}
	private java.util.Date tranDate;
	
	private java.lang.Integer pwdDayErr;
	private java.lang.Integer PwdTotErr;

	public java.lang.Integer getPwdTotErr() {
		return PwdTotErr;
	}

	public void setPwdTotErr(java.lang.Integer pwdTotErr) {
		PwdTotErr = pwdTotErr;
	}

	public java.lang.Integer getPwdDayErr() {
		return pwdDayErr;
	}

	public void setPwdDayErr(java.lang.Integer pwdDayErr) {
		this.pwdDayErr = pwdDayErr;
	}

	public java.util.Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(java.util.Date tranDate) {
		this.tranDate = tranDate;
	}

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getCardNo() {
		return this.cardNo;
	}
	
	public void setCardNo(java.lang.String value) {
		this.cardNo = value;
	}
	
	public java.lang.String getCardSeq() {
		return this.cardSeq;
	}
	
	public void setCardSeq(java.lang.String value) {
		this.cardSeq = value;
	}
	
	public java.lang.String getCardKind() {
		return this.cardKind;
	}
	
	public void setCardKind(java.lang.String value) {
		this.cardKind = value;
	}
	
	public java.lang.String getCardType() {
		return this.cardType;
	}
	
	public void setCardType(java.lang.String value) {
		this.cardType = value;
	}
	
	public java.lang.String getOpenBrc() {
		return this.openBrc;
	}
	
	public void setOpenBrc(java.lang.String value) {
		this.openBrc = value;
	}
	
	public java.lang.String getOpenOpr() {
		return this.openOpr;
	}
	
	public void setOpenOpr(java.lang.String value) {
		this.openOpr = value;
	}
	
	public java.lang.String getMkcardStat() {
		return this.mkcardStat;
	}
	
	public void setMkcardStat(java.lang.String value) {
		this.mkcardStat = value;
	}
	
	public java.lang.String getCardStat() {
		return this.cardStat;
	}
	
	public void setCardStat(java.lang.String value) {
		this.cardStat = value;
	}
	
	public java.lang.String getStatRemark() {
		return this.statRemark;
	}
	
	public void setStatRemark(java.lang.String value) {
		this.statRemark = value;
	}
	
	public java.lang.String getVertifyNum() {
		return this.vertifyNum;
	}
	
	public void setVertifyNum(java.lang.String value) {
		this.vertifyNum = value;
	}
	
	public java.util.Date getExpireDateBegin() {
		return this.expireDateBegin;
	}
	
	public void setExpireDateBegin(java.util.Date value) {
		this.expireDateBegin = value;
	}	
	
	public java.util.Date getExpireDateEnd() {
		return this.expireDateEnd;
	}
	
	public void setExpireDateEnd(java.util.Date value) {
		this.expireDateEnd = value;
	}
	
	public java.lang.String getServiceCode() {
		return this.serviceCode;
	}
	
	public void setServiceCode(java.lang.String value) {
		this.serviceCode = value;
	}
	
	public java.lang.String getOldCardNo() {
		return this.oldCardNo;
	}
	
	public void setOldCardNo(java.lang.String value) {
		this.oldCardNo = value;
	}
	
	public java.lang.String getOldCardSeq() {
		return this.oldCardSeq;
	}
	
	public void setOldCardSeq(java.lang.String value) {
		this.oldCardSeq = value;
	}
	
	public java.util.Date getOpenDateBegin() {
		return this.openDateBegin;
	}
	
	public void setOpenDateBegin(java.util.Date value) {
		this.openDateBegin = value;
	}	
	
	public java.util.Date getOpenDateEnd() {
		return this.openDateEnd;
	}
	
	public void setOpenDateEnd(java.util.Date value) {
		this.openDateEnd = value;
	}
	
	public java.util.Date getBackDateBegin() {
		return this.backDateBegin;
	}
	
	public void setBackDateBegin(java.util.Date value) {
		this.backDateBegin = value;
	}	
	
	public java.util.Date getBackDateEnd() {
		return this.backDateEnd;
	}
	
	public void setBackDateEnd(java.util.Date value) {
		this.backDateEnd = value;
	}
	
	public java.util.Date getCloseDateBegin() {
		return this.closeDateBegin;
	}
	
	public void setCloseDateBegin(java.util.Date value) {
		this.closeDateBegin = value;
	}	
	
	public java.util.Date getCloseDateEnd() {
		return this.closeDateEnd;
	}
	
	public void setCloseDateEnd(java.util.Date value) {
		this.closeDateEnd = value;
	}
	
	public java.lang.String getCustomNo() {
		return this.customNo;
	}
	
	public void setCustomNo(java.lang.String value) {
		this.customNo = value;
	}
	
	public java.lang.String getCertType() {
		return this.certType;
	}
	
	public void setCertType(java.lang.String value) {
		this.certType = value;
	}
	
	public java.lang.String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(java.lang.String value) {
		this.certNo = value;
	}
	
	public java.lang.String getCustName() {
		return this.custName;
	}
	
	public void setCustName(java.lang.String value) {
		this.custName = value;
	}
	
	public java.lang.String getCustEname() {
		return this.custEname;
	}
	
	public void setCustEname(java.lang.String value) {
		this.custEname = value;
	}
	
	public java.lang.String getCustKind() {
		return this.custKind;
	}
	
	public void setCustKind(java.lang.String value) {
		this.custKind = value;
	}
	
	public java.lang.String getVouNo() {
		return this.vouNo;
	}
	
	public void setVouNo(java.lang.String value) {
		this.vouNo = value;
	}
	
	public java.lang.String getCardAcctType() {
		return this.cardAcctType;
	}
	
	public void setCardAcctType(java.lang.String value) {
		this.cardAcctType = value;
	}
	
	public java.lang.String getMainCardNo() {
		return this.mainCardNo;
	}
	
	public void setMainCardNo(java.lang.String value) {
		this.mainCardNo = value;
	}
	
	public java.lang.String getEmpFlg() {
		return this.empFlg;
	}
	
	public void setEmpFlg(java.lang.String value) {
		this.empFlg = value;
	}
	
	public java.lang.String getCardFlag() {
		return this.cardFlag;
	}
	
	public void setCardFlag(java.lang.String value) {
		this.cardFlag = value;
	}
	
	public java.lang.String getCardPhySort() {
		return this.cardPhySort;
	}
	
	public void setCardPhySort(java.lang.String value) {
		this.cardPhySort = value;
	}
	
	public java.lang.String getNameFlag() {
		return this.nameFlag;
	}
	
	public void setNameFlag(java.lang.String value) {
		this.nameFlag = value;
	}
	
	public java.lang.String getPhotoFlag() {
		return this.photoFlag;
	}
	
	public void setPhotoFlag(java.lang.String value) {
		this.photoFlag = value;
	}
	
	public java.lang.String getIcFlg() {
		return this.icFlg;
	}
	
	public void setIcFlg(java.lang.String value) {
		this.icFlg = value;
	}
	
	public java.lang.String getFeeTp() {
		return this.feeTp;
	}
	
	public void setFeeTp(java.lang.String value) {
		this.feeTp = value;
	}
	
	public java.lang.String getDefAcflg() {
		return this.defAcflg;
	}
	
	public void setDefAcflg(java.lang.String value) {
		this.defAcflg = value;
	}
	
	public java.math.BigDecimal getFeeAt() {
		return this.feeAt;
	}
	
	public void setFeeAt(java.math.BigDecimal value) {
		this.feeAt = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

