package com.opms.conmg.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class CmbankQuery implements Serializable {
    

	/** PK */
	private java.lang.String id;
	/** 分行 */
	private java.lang.String branch;
	/** 名称 */
	private java.lang.String branchName;
	/** 简称 */
	private java.lang.String branchShort;
	/** 是否签发支票 */
	private java.lang.String chequeIssuingBranch;
	/** 内部客户号 */
	private java.lang.String internalClient;
	/** 公司 */
	private java.lang.String company;
	/** 国家 */
	private java.lang.String country;
	/** 州、省 */
	private java.lang.String state;
	/** 基本币种 */
	private java.lang.String baseCcy;
	/** 本地币种 */
	private java.lang.String localCcy;
	/** profitSegment */
	private java.lang.String profitSegment;
	/** 层次代码 */
	private java.math.BigDecimal hierarchyCode;
	/** 隶属于 */
	private java.lang.String attachedTo;
	/** 是否为交易分行 */
	private java.lang.String tranBrInd;
	/** 支行代码 */
	private java.lang.String subBranchCode;
	/** 业务单元（CNY） */
	private java.lang.String cnyBusinessUnit;
	/** 业务单元（本币） */
	private java.lang.String hkdBusinessUnit;
	/** 外汇登记号 */
	private java.lang.String fxOrganCode;
	/** 私人银行 */
	private java.lang.String personalBank;
	/** 虚拟银行 */
	private java.lang.String virtualBank;
	/** 实际银行 */
	private java.lang.String controlBranch;
	/** practiceBranch */
	private java.lang.String practiceBranch;
	/** dummyBank */
	private java.lang.String dummyBank;
	/** 机构代码(一般等于FX_ORGAN_CODE)     */
	private java.lang.String fxBankCode;
	/** 异地支行标志 */
	private java.lang.String isotherplace;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getBranch() {
		return this.branch;
	}
	
	public void setBranch(java.lang.String value) {
		this.branch = value;
	}
	
	public java.lang.String getBranchName() {
		return this.branchName;
	}
	
	public void setBranchName(java.lang.String value) {
		this.branchName = value;
	}
	
	public java.lang.String getBranchShort() {
		return this.branchShort;
	}
	
	public void setBranchShort(java.lang.String value) {
		this.branchShort = value;
	}
	
	public java.lang.String getChequeIssuingBranch() {
		return this.chequeIssuingBranch;
	}
	
	public void setChequeIssuingBranch(java.lang.String value) {
		this.chequeIssuingBranch = value;
	}
	
	public java.lang.String getInternalClient() {
		return this.internalClient;
	}
	
	public void setInternalClient(java.lang.String value) {
		this.internalClient = value;
	}
	
	public java.lang.String getCompany() {
		return this.company;
	}
	
	public void setCompany(java.lang.String value) {
		this.company = value;
	}
	
	public java.lang.String getCountry() {
		return this.country;
	}
	
	public void setCountry(java.lang.String value) {
		this.country = value;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setState(java.lang.String value) {
		this.state = value;
	}
	
	public java.lang.String getBaseCcy() {
		return this.baseCcy;
	}
	
	public void setBaseCcy(java.lang.String value) {
		this.baseCcy = value;
	}
	
	public java.lang.String getLocalCcy() {
		return this.localCcy;
	}
	
	public void setLocalCcy(java.lang.String value) {
		this.localCcy = value;
	}
	
	public java.lang.String getProfitSegment() {
		return this.profitSegment;
	}
	
	public void setProfitSegment(java.lang.String value) {
		this.profitSegment = value;
	}
	
	public java.math.BigDecimal getHierarchyCode() {
		return this.hierarchyCode;
	}
	
	public void setHierarchyCode(java.math.BigDecimal value) {
		this.hierarchyCode = value;
	}
	
	public java.lang.String getAttachedTo() {
		return this.attachedTo;
	}
	
	public void setAttachedTo(java.lang.String value) {
		this.attachedTo = value;
	}
	
	public java.lang.String getTranBrInd() {
		return this.tranBrInd;
	}
	
	public void setTranBrInd(java.lang.String value) {
		this.tranBrInd = value;
	}
	
	public java.lang.String getSubBranchCode() {
		return this.subBranchCode;
	}
	
	public void setSubBranchCode(java.lang.String value) {
		this.subBranchCode = value;
	}
	
	public java.lang.String getCnyBusinessUnit() {
		return this.cnyBusinessUnit;
	}
	
	public void setCnyBusinessUnit(java.lang.String value) {
		this.cnyBusinessUnit = value;
	}
	
	public java.lang.String getHkdBusinessUnit() {
		return this.hkdBusinessUnit;
	}
	
	public void setHkdBusinessUnit(java.lang.String value) {
		this.hkdBusinessUnit = value;
	}
	
	public java.lang.String getFxOrganCode() {
		return this.fxOrganCode;
	}
	
	public void setFxOrganCode(java.lang.String value) {
		this.fxOrganCode = value;
	}
	
	public java.lang.String getPersonalBank() {
		return this.personalBank;
	}
	
	public void setPersonalBank(java.lang.String value) {
		this.personalBank = value;
	}
	
	public java.lang.String getVirtualBank() {
		return this.virtualBank;
	}
	
	public void setVirtualBank(java.lang.String value) {
		this.virtualBank = value;
	}
	
	public java.lang.String getControlBranch() {
		return this.controlBranch;
	}
	
	public void setControlBranch(java.lang.String value) {
		this.controlBranch = value;
	}
	
	public java.lang.String getPracticeBranch() {
		return this.practiceBranch;
	}
	
	public void setPracticeBranch(java.lang.String value) {
		this.practiceBranch = value;
	}
	
	public java.lang.String getDummyBank() {
		return this.dummyBank;
	}
	
	public void setDummyBank(java.lang.String value) {
		this.dummyBank = value;
	}
	
	public java.lang.String getFxBankCode() {
		return this.fxBankCode;
	}
	
	public void setFxBankCode(java.lang.String value) {
		this.fxBankCode = value;
	}
	
	public java.lang.String getIsotherplace() {
		return this.isotherplace;
	}
	
	public void setIsotherplace(java.lang.String value) {
		this.isotherplace = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

