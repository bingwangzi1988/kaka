package com.opms.osgi.po;

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
@Table(name = "OPMS_SYSTEM_CFG")
public class OpmsSystemCfg extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "系统配置";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_HOST_IP = "主机IP";
	public static final String ALIAS_HOST_PORT = "主机端口";
	public static final String ALIAS_SYSTEM_ID = "系统标识";
	public static final String ALIAS_STATE = "开启标志";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 主机IP       db_column: HOST_IP 
     */ 	
	@NotBlank @Length(max=15)
	private java.lang.String hostIp;
    /**
     * 主机端口       db_column: HOST_PORT 
     */ 	
	@NotNull 
	private java.math.BigDecimal hostPort;
    /**
     * 系统标识       db_column: SYSTEM_ID 
     */ 	
	@NotBlank @Length(max=50)
	private java.lang.String systemId;
    /**
     * 开启标志       db_column: STATE 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String state;
	//columns END


	public OpmsSystemCfg(){
	}

	public OpmsSystemCfg(
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
	
	@Column(name = "SYSTEM_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public java.lang.String getSystemId() {
		return this.systemId;
	}
	
	public void setSystemId(java.lang.String value) {
		this.systemId = value;
	}
	
	@Column(name = "STATE", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setState(java.lang.String value) {
		this.state = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("HostIp",getHostIp())
			.append("HostPort",getHostPort())
			.append("SystemId",getSystemId())
			.append("State",getState())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OpmsSystemCfg == false) return false;
		if(this == obj) return true;
		OpmsSystemCfg other = (OpmsSystemCfg)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

