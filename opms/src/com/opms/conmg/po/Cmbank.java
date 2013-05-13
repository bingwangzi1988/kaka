package com.opms.conmg.po;

import cn.org.rapid_framework.util.DateConvertUtils;
import com.common.base.BaseEntity;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "CMBANK")
public class Cmbank extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "������Ϣ��";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BRANCH = "����";
	public static final String ALIAS_BRANCH_NAME = "����";
	public static final String ALIAS_BRANCH_SHORT = "���";
	public static final String ALIAS_CHEQUE_ISSUING_BRANCH = "�Ƿ�ǩ��֧Ʊ";
	public static final String ALIAS_INTERNAL_CLIENT = "�ڲ��ͻ���";
	public static final String ALIAS_COMPANY = "��˾";
	public static final String ALIAS_COUNTRY = "����";
	public static final String ALIAS_STATE = "�ݡ�ʡ";
	public static final String ALIAS_BASE_CCY = "��������";
	public static final String ALIAS_LOCAL_CCY = "���ر���";
	public static final String ALIAS_PROFIT_SEGMENT = "profitSegment";
	public static final String ALIAS_HIERARCHY_CODE = "��δ���";
	public static final String ALIAS_ATTACHED_TO = "������";
	public static final String ALIAS_TRAN_BR_IND = "�Ƿ�Ϊ���׷���";
	public static final String ALIAS_SUB_BRANCH_CODE = "֧�д���";
	public static final String ALIAS_CNY_BUSINESS_UNIT = "ҵ��Ԫ��CNY��";
	public static final String ALIAS_HKD_BUSINESS_UNIT = "ҵ��Ԫ�����ң�";
	public static final String ALIAS_FX_ORGAN_CODE = "���ǼǺ�";
	public static final String ALIAS_PERSONAL_BANK = "˽������";
	public static final String ALIAS_VIRTUAL_BANK = "��������";
	public static final String ALIAS_CONTROL_BRANCH = "ʵ������";
	public static final String ALIAS_PRACTICE_BRANCH = "practiceBranch";
	public static final String ALIAS_DUMMY_BANK = "dummyBank";
	public static final String ALIAS_FX_BANK_CODE = "��������(һ�����FX_ORGAN_CODE)    ";
	public static final String ALIAS_ISOTHERPLACE = "���֧�б�־";
	
	//date formats
	

	//����ֱ��ʹ��: @Length(max=50,message="�û������Ȳ��ܴ���50")��ʾ������Ϣ
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * ����       db_column: BRANCH 
     */ 	
	@NotBlank @Length(max=6)
	private java.lang.String branch;
    /**
     * ����       db_column: BRANCH_NAME 
     */ 	
	@NotBlank @Length(max=120)
	private java.lang.String branchName;
    /**
     * ���       db_column: BRANCH_SHORT 
     */ 	
	@Length(max=80)
	private java.lang.String branchShort;
    /**
     * �Ƿ�ǩ��֧Ʊ       db_column: CHEQUE_ISSUING_BRANCH 
     */ 	
	@Length(max=1)
	private java.lang.String chequeIssuingBranch;
    /**
     * �ڲ��ͻ���       db_column: INTERNAL_CLIENT 
     */ 	
	@Length(max=12)
	private java.lang.String internalClient;
    /**
     * ��˾       db_column: COMPANY 
     */ 	
	@Length(max=8)
	private java.lang.String company;
    /**
     * ����       db_column: COUNTRY 
     */ 	
	@Length(max=2)
	private java.lang.String country;
    /**
     * �ݡ�ʡ       db_column: STATE 
     */ 	
	@Length(max=2)
	private java.lang.String state;
    /**
     * ��������       db_column: BASE_CCY 
     */ 	
	@Length(max=3)
	private java.lang.String baseCcy;
    /**
     * ���ر���       db_column: LOCAL_CCY 
     */ 	
	@Length(max=3)
	private java.lang.String localCcy;
    /**
     * profitSegment       db_column: PROFIT_SEGMENT 
     */ 	
	@Length(max=12)
	private java.lang.String profitSegment;
    /**
     * ��δ���       db_column: HIERARCHY_CODE 
     */ 	
	
	private java.math.BigDecimal hierarchyCode;
    /**
     * ������       db_column: ATTACHED_TO 
     */ 	
	@Length(max=6)
	private java.lang.String attachedTo;
    /**
     * �Ƿ�Ϊ���׷���       db_column: TRAN_BR_IND 
     */ 	
	@Length(max=1)
	private java.lang.String tranBrInd;
    /**
     * ֧�д���       db_column: SUB_BRANCH_CODE 
     */ 	
	@Length(max=10)
	private java.lang.String subBranchCode;
    /**
     * ҵ��Ԫ��CNY��       db_column: CNY_BUSINESS_UNIT 
     */ 	
	@Length(max=5)
	private java.lang.String cnyBusinessUnit;
    /**
     * ҵ��Ԫ�����ң�       db_column: HKD_BUSINESS_UNIT 
     */ 	
	@Length(max=5)
	private java.lang.String hkdBusinessUnit;
    /**
     * ���ǼǺ�       db_column: FX_ORGAN_CODE 
     */ 	
	@Length(max=12)
	private java.lang.String fxOrganCode;
    /**
     * ˽������       db_column: PERSONAL_BANK 
     */ 	
	@Length(max=1)
	private java.lang.String personalBank;
    /**
     * ��������       db_column: VIRTUAL_BANK 
     */ 	
	@Length(max=1)
	private java.lang.String virtualBank;
    /**
     * ʵ������       db_column: CONTROL_BRANCH 
     */ 	
	@Length(max=6)
	private java.lang.String controlBranch;
    /**
     * practiceBranch       db_column: PRACTICE_BRANCH 
     */ 	
	@Length(max=6)
	private java.lang.String practiceBranch;
    /**
     * dummyBank       db_column: DUMMY_BANK 
     */ 	
	@Length(max=1)
	private java.lang.String dummyBank;
    /**
     * ��������(һ�����FX_ORGAN_CODE)           db_column: FX_BANK_CODE 
     */ 	
	@Length(max=14)
	private java.lang.String fxBankCode;
    /**
     * ���֧�б�־       db_column: ISOTHERPLACE 
     */ 	
	@Length(max=1)
	private java.lang.String isotherplace;
	//columns END


	public Cmbank(){
	}

	public Cmbank(
		java.lang.String id
	){
		this.id = id;
	}

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid")
	@Column(name = "ID", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "BRANCH", unique = false, nullable = false, insertable = true, updatable = true, length = 6)
	public java.lang.String getBranch() {
		return this.branch;
	}
	
	public void setBranch(java.lang.String value) {
		this.branch = value;
	}
	
	@Column(name = "BRANCH_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 120)
	public java.lang.String getBranchName() {
		return this.branchName;
	}
	
	public void setBranchName(java.lang.String value) {
		this.branchName = value;
	}
	
	@Column(name = "BRANCH_SHORT", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public java.lang.String getBranchShort() {
		return this.branchShort;
	}
	
	public void setBranchShort(java.lang.String value) {
		this.branchShort = value;
	}
	
	@Column(name = "CHEQUE_ISSUING_BRANCH", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getChequeIssuingBranch() {
		return this.chequeIssuingBranch;
	}
	
	public void setChequeIssuingBranch(java.lang.String value) {
		this.chequeIssuingBranch = value;
	}
	
	@Column(name = "INTERNAL_CLIENT", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	public java.lang.String getInternalClient() {
		return this.internalClient;
	}
	
	public void setInternalClient(java.lang.String value) {
		this.internalClient = value;
	}
	
	@Column(name = "COMPANY", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	public java.lang.String getCompany() {
		return this.company;
	}
	
	public void setCompany(java.lang.String value) {
		this.company = value;
	}
	
	@Column(name = "COUNTRY", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getCountry() {
		return this.country;
	}
	
	public void setCountry(java.lang.String value) {
		this.country = value;
	}
	
	@Column(name = "STATE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setState(java.lang.String value) {
		this.state = value;
	}
	
	@Column(name = "BASE_CCY", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public java.lang.String getBaseCcy() {
		return this.baseCcy;
	}
	
	public void setBaseCcy(java.lang.String value) {
		this.baseCcy = value;
	}
	
	@Column(name = "LOCAL_CCY", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public java.lang.String getLocalCcy() {
		return this.localCcy;
	}
	
	public void setLocalCcy(java.lang.String value) {
		this.localCcy = value;
	}
	
	@Column(name = "PROFIT_SEGMENT", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	public java.lang.String getProfitSegment() {
		return this.profitSegment;
	}
	
	public void setProfitSegment(java.lang.String value) {
		this.profitSegment = value;
	}
	
	@Column(name = "HIERARCHY_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public java.math.BigDecimal getHierarchyCode() {
		return this.hierarchyCode;
	}
	
	public void setHierarchyCode(java.math.BigDecimal value) {
		this.hierarchyCode = value;
	}
	
	@Column(name = "ATTACHED_TO", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getAttachedTo() {
		return this.attachedTo;
	}
	
	public void setAttachedTo(java.lang.String value) {
		this.attachedTo = value;
	}
	
	@Column(name = "TRAN_BR_IND", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getTranBrInd() {
		return this.tranBrInd;
	}
	
	public void setTranBrInd(java.lang.String value) {
		this.tranBrInd = value;
	}
	
	@Column(name = "SUB_BRANCH_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getSubBranchCode() {
		return this.subBranchCode;
	}
	
	public void setSubBranchCode(java.lang.String value) {
		this.subBranchCode = value;
	}
	
	@Column(name = "CNY_BUSINESS_UNIT", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public java.lang.String getCnyBusinessUnit() {
		return this.cnyBusinessUnit;
	}
	
	public void setCnyBusinessUnit(java.lang.String value) {
		this.cnyBusinessUnit = value;
	}
	
	@Column(name = "HKD_BUSINESS_UNIT", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public java.lang.String getHkdBusinessUnit() {
		return this.hkdBusinessUnit;
	}
	
	public void setHkdBusinessUnit(java.lang.String value) {
		this.hkdBusinessUnit = value;
	}
	
	@Column(name = "FX_ORGAN_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	public java.lang.String getFxOrganCode() {
		return this.fxOrganCode;
	}
	
	public void setFxOrganCode(java.lang.String value) {
		this.fxOrganCode = value;
	}
	
	@Column(name = "PERSONAL_BANK", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getPersonalBank() {
		return this.personalBank;
	}
	
	public void setPersonalBank(java.lang.String value) {
		this.personalBank = value;
	}
	
	@Column(name = "VIRTUAL_BANK", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getVirtualBank() {
		return this.virtualBank;
	}
	
	public void setVirtualBank(java.lang.String value) {
		this.virtualBank = value;
	}
	
	@Column(name = "CONTROL_BRANCH", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getControlBranch() {
		return this.controlBranch;
	}
	
	public void setControlBranch(java.lang.String value) {
		this.controlBranch = value;
	}
	
	@Column(name = "PRACTICE_BRANCH", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getPracticeBranch() {
		return this.practiceBranch;
	}
	
	public void setPracticeBranch(java.lang.String value) {
		this.practiceBranch = value;
	}
	
	@Column(name = "DUMMY_BANK", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getDummyBank() {
		return this.dummyBank;
	}
	
	public void setDummyBank(java.lang.String value) {
		this.dummyBank = value;
	}
	
	@Column(name = "FX_BANK_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 14)
	public java.lang.String getFxBankCode() {
		return this.fxBankCode;
	}
	
	public void setFxBankCode(java.lang.String value) {
		this.fxBankCode = value;
	}
	
	@Column(name = "ISOTHERPLACE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getIsotherplace() {
		return this.isotherplace;
	}
	
	public void setIsotherplace(java.lang.String value) {
		this.isotherplace = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Branch",getBranch())
			.append("BranchName",getBranchName())
			.append("BranchShort",getBranchShort())
			.append("ChequeIssuingBranch",getChequeIssuingBranch())
			.append("InternalClient",getInternalClient())
			.append("Company",getCompany())
			.append("Country",getCountry())
			.append("State",getState())
			.append("BaseCcy",getBaseCcy())
			.append("LocalCcy",getLocalCcy())
			.append("ProfitSegment",getProfitSegment())
			.append("HierarchyCode",getHierarchyCode())
			.append("AttachedTo",getAttachedTo())
			.append("TranBrInd",getTranBrInd())
			.append("SubBranchCode",getSubBranchCode())
			.append("CnyBusinessUnit",getCnyBusinessUnit())
			.append("HkdBusinessUnit",getHkdBusinessUnit())
			.append("FxOrganCode",getFxOrganCode())
			.append("PersonalBank",getPersonalBank())
			.append("VirtualBank",getVirtualBank())
			.append("ControlBranch",getControlBranch())
			.append("PracticeBranch",getPracticeBranch())
			.append("DummyBank",getDummyBank())
			.append("FxBankCode",getFxBankCode())
			.append("Isotherplace",getIsotherplace())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Cmbank == false) return false;
		if(this == obj) return true;
		Cmbank other = (Cmbank)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

