package com.opms.conmg.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class CmbankQuery implements Serializable {
    

	/** PK */
	private java.lang.String id;
	/** ���� */
	private java.lang.String branch;
	/** ���� */
	private java.lang.String branchName;
	/** ��� */
	private java.lang.String branchShort;
	/** �Ƿ�ǩ��֧Ʊ */
	private java.lang.String chequeIssuingBranch;
	/** �ڲ��ͻ��� */
	private java.lang.String internalClient;
	/** ��˾ */
	private java.lang.String company;
	/** ���� */
	private java.lang.String country;
	/** �ݡ�ʡ */
	private java.lang.String state;
	/** �������� */
	private java.lang.String baseCcy;
	/** ���ر��� */
	private java.lang.String localCcy;
	/** profitSegment */
	private java.lang.String profitSegment;
	/** ��δ��� */
	private java.math.BigDecimal hierarchyCode;
	/** ������ */
	private java.lang.String attachedTo;
	/** �Ƿ�Ϊ���׷��� */
	private java.lang.String tranBrInd;
	/** ֧�д��� */
	private java.lang.String subBranchCode;
	/** ҵ��Ԫ��CNY�� */
	private java.lang.String cnyBusinessUnit;
	/** ҵ��Ԫ�����ң� */
	private java.lang.String hkdBusinessUnit;
	/** ���ǼǺ� */
	private java.lang.String fxOrganCode;
	/** ˽������ */
	private java.lang.String personalBank;
	/** �������� */
	private java.lang.String virtualBank;
	/** ʵ������ */
	private java.lang.String controlBranch;
	/** practiceBranch */
	private java.lang.String practiceBranch;
	/** dummyBank */
	private java.lang.String dummyBank;
	/** ��������(һ�����FX_ORGAN_CODE)     */
	private java.lang.String fxBankCode;
	/** ���֧�б�־ */
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

