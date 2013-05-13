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
@Table(name = "EAOPERATERCD")
public class Eaoperatercd extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "������¼��";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "����Ա����";
	public static final String ALIAS_AUTHORIZERID = "��ȨԱ����";
	public static final String ALIAS_OPERATEDATE = "��Ȩʱ��";
	public static final String ALIAS_OPERATETYPE = "��������";
	public static final String ALIAS_OPERATEOBJECT = "��������";
	public static final String ALIAS_OPERATEINFO = "������Ϣ";
	
	//date formats
	public static final String FORMAT_OPERATEDATE = DATE_FORMAT;
	

	//����ֱ��ʹ��: @Length(max=50,message="�û������Ȳ��ܴ���50")��ʾ������Ϣ
	//columns START
    /**
     * id       db_column: ID 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String id;
    /**
     * ����Ա����       db_column: USERID 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String userid;
    /**
     * ��ȨԱ����       db_column: AUTHORIZERID 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String authorizerid;
    /**
     * ��Ȩʱ��       db_column: OPERATEDATE 
     */ 	
	@NotNull 
	private java.util.Date operatedate;
    /**
     * ��������       db_column: OPERATETYPE 
     */ 	
	@NotBlank @Length(max=20)
	private java.lang.String operatetype;
    /**
     * ��������       db_column: OPERATEOBJECT 
     */ 	
	@NotBlank @Length(max=60)
	private java.lang.String operateobject;
    /**
     * ������Ϣ       db_column: OPERATEINFO 
     */ 	
	@Length(max=255)
	private java.lang.String operateinfo;
	//columns END


	public Eaoperatercd(){
	}

	public Eaoperatercd(
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
	
	@Column(name = "USERID", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getUserid() {
		return this.userid;
	}
	
	public void setUserid(java.lang.String value) {
		this.userid = value;
	}
	
	@Column(name = "AUTHORIZERID", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getAuthorizerid() {
		return this.authorizerid;
	}
	
	public void setAuthorizerid(java.lang.String value) {
		this.authorizerid = value;
	}
	
	@Transient
	public String getOperatedateString() {
		return DateConvertUtils.format(getOperatedate(), FORMAT_OPERATEDATE);
	}
	public void setOperatedateString(String value) {
		setOperatedate(DateConvertUtils.parse(value, FORMAT_OPERATEDATE,java.util.Date.class));
	}
	
	@Column(name = "OPERATEDATE", unique = false, nullable = false, insertable = true, updatable = true, length = 7)
	public java.util.Date getOperatedate() {
		return this.operatedate;
	}
	
	public void setOperatedate(java.util.Date value) {
		this.operatedate = value;
	}
	
	@Column(name = "OPERATETYPE", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public java.lang.String getOperatetype() {
		return this.operatetype;
	}
	
	public void setOperatetype(java.lang.String value) {
		this.operatetype = value;
	}
	
	@Column(name = "OPERATEOBJECT", unique = false, nullable = false, insertable = true, updatable = true, length = 60)
	public java.lang.String getOperateobject() {
		return this.operateobject;
	}
	
	public void setOperateobject(java.lang.String value) {
		this.operateobject = value;
	}
	
	@Column(name = "OPERATEINFO", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public java.lang.String getOperateinfo() {
		return this.operateinfo;
	}
	
	public void setOperateinfo(java.lang.String value) {
		this.operateinfo = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Userid",getUserid())
			.append("Authorizerid",getAuthorizerid())
			.append("Operatedate",getOperatedate())
			.append("Operatetype",getOperatetype())
			.append("Operateobject",getOperateobject())
			.append("Operateinfo",getOperateinfo())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Eaoperatercd == false) return false;
		if(this == obj) return true;
		Eaoperatercd other = (Eaoperatercd)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

