package com.opms.osgi.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class OpmsHostCfgQuery implements Serializable {
    

	/** PK */
	private java.lang.String id;
	/** ������ */
	private java.lang.String hostName;
	/** ����IP */
	private java.lang.String hostIp;
	/** �����˿� */
	private java.math.BigDecimal hostPort;
	/** ������־ */
	private java.lang.String state;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getHostName() {
		return this.hostName;
	}
	
	public void setHostName(java.lang.String value) {
		this.hostName = value;
	}
	
	public java.lang.String getHostIp() {
		return this.hostIp;
	}
	
	public void setHostIp(java.lang.String value) {
		this.hostIp = value;
	}
	
	public java.math.BigDecimal getHostPort() {
		return this.hostPort;
	}
	
	public void setHostPort(java.math.BigDecimal value) {
		this.hostPort = value;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setState(java.lang.String value) {
		this.state = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

