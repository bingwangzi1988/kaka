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
@Table(name = "CMPARM")
public class Cmparm extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "ҵ�������";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_PARM_ID = "������ʶ";
	public static final String ALIAS_PARM_FLAG = "������־";
	public static final String ALIAS_PARM_NAME = "��������";
	public static final String ALIAS_PARM_DESC = "��������";
	public static final String ALIAS_PARM_VALUE = "����ֵ";
	public static final String ALIAS_MEMO = "����";
	public static final String ALIAS_MEMO1 = "����һ";
	
	//date formats
	

	//����ֱ��ʹ��: @Length(max=50,message="�û������Ȳ��ܴ���50")��ʾ������Ϣ
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * ������ʶ       db_column: PARM_ID 
     */ 	
	@NotBlank @Length(max=20)
	private java.lang.String parmId;
    /**
     * ������־       db_column: PARM_FLAG 
     */ 	
	@NotBlank @Length(max=2)
	private java.lang.String parmFlag;
    /**
     * ��������       db_column: PARM_NAME 
     */ 	
	@NotBlank @Length(max=60)
	private java.lang.String parmName;
    /**
     * ��������       db_column: PARM_DESC 
     */ 	
	@NotBlank @Length(max=60)
	private java.lang.String parmDesc;
    /**
     * ����ֵ       db_column: PARM_VALUE 
     */ 	
	@NotBlank @Length(max=128)
	private java.lang.String parmValue;
    /**
     * ����       db_column: MEMO 
     */ 	
	@Length(max=40)
	private java.lang.String memo;
    /**
     * ����һ       db_column: MEMO_1 
     */ 	
	@Length(max=60)
	private java.lang.String memo1;
	//columns END


	public Cmparm(){
	}

	public Cmparm(
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
	
	@Column(name = "PARM_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public java.lang.String getParmId() {
		return this.parmId;
	}
	
	public void setParmId(java.lang.String value) {
		this.parmId = value;
	}
	
	@Column(name = "PARM_FLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	public java.lang.String getParmFlag() {
		return this.parmFlag;
	}
	
	public void setParmFlag(java.lang.String value) {
		this.parmFlag = value;
	}
	
	@Column(name = "PARM_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 60)
	public java.lang.String getParmName() {
		return this.parmName;
	}
	
	public void setParmName(java.lang.String value) {
		this.parmName = value;
	}
	
	@Column(name = "PARM_DESC", unique = false, nullable = false, insertable = true, updatable = true, length = 60)
	public java.lang.String getParmDesc() {
		return this.parmDesc;
	}
	
	public void setParmDesc(java.lang.String value) {
		this.parmDesc = value;
	}
	
	@Column(name = "PARM_VALUE", unique = false, nullable = false, insertable = true, updatable = true, length = 128)
	public java.lang.String getParmValue() {
		return this.parmValue;
	}
	
	public void setParmValue(java.lang.String value) {
		this.parmValue = value;
	}
	
	@Column(name = "MEMO", unique = false, nullable = true, insertable = true, updatable = true, length = 40)
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	@Column(name = "MEMO_1", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public java.lang.String getMemo1() {
		return this.memo1;
	}
	
	public void setMemo1(java.lang.String value) {
		this.memo1 = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ParmId",getParmId())
			.append("ParmFlag",getParmFlag())
			.append("ParmName",getParmName())
			.append("ParmDesc",getParmDesc())
			.append("ParmValue",getParmValue())
			.append("Memo",getMemo())
			.append("Memo1",getMemo1())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Cmparm == false) return false;
		if(this == obj) return true;
		Cmparm other = (Cmparm)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

