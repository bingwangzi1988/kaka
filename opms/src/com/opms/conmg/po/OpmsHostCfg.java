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
@Table(name = "OPMS_HOST_CFG")
public class OpmsHostCfg extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "��������";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_HOST_NAME = "������";
	public static final String ALIAS_HOST_IP = "����IP";
	public static final String ALIAS_HOST_PORT = "�����˿�";
	public static final String ALIAS_CFG_TYPE = "���ͣ�0���������ع���1��������";
	
	//date formats
	

	//����ֱ��ʹ��: @Length(max=50,message="�û������Ȳ��ܴ���50")��ʾ������Ϣ
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * ������       db_column: HOST_NAME 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String hostName;
    /**
     * ����IP       db_column: HOST_IP 
     */ 	
	@NotBlank @Length(max=15)
	private java.lang.String hostIp;
    /**
     * �����˿�       db_column: HOST_PORT 
     */ 	
	@NotNull 
	private java.math.BigDecimal hostPort;
    /**
     * ���ͣ�0���������ع���1��������       db_column: CFG_TYPE 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String cfgType;
	//columns END


	public OpmsHostCfg(){
	}

	public OpmsHostCfg(
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
	
	@Column(name = "HOST_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getHostName() {
		return this.hostName;
	}
	
	public void setHostName(java.lang.String value) {
		this.hostName = value;
	}
	
	@Column(name = "HOST_IP", unique = false, nullable = false, insertable = true, updatable = true, length = 15)
	public java.lang.String getHostIp() {
		return this.hostIp;
	}
	
	public void setHostIp(java.lang.String value) {
		this.hostIp = value;
	}
	
	@Column(name = "HOST_PORT", unique = false, nullable = false, insertable = true, updatable = true, length = 22)
	public java.math.BigDecimal getHostPort() {
		return this.hostPort;
	}
	
	public void setHostPort(java.math.BigDecimal value) {
		this.hostPort = value;
	}
	
	@Column(name = "CFG_TYPE", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getCfgType() {
		return this.cfgType;
	}
	
	public void setCfgType(java.lang.String value) {
		this.cfgType = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("HostName",getHostName())
			.append("HostIp",getHostIp())
			.append("HostPort",getHostPort())
			.append("CfgType",getCfgType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OpmsHostCfg == false) return false;
		if(this == obj) return true;
		OpmsHostCfg other = (OpmsHostCfg)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

