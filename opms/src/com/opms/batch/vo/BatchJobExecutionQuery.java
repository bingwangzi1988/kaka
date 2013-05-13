package com.opms.batch.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class BatchJobExecutionQuery implements Serializable {
    

	/** PK */
	private java.math.BigDecimal id;
	/** ������ҵ���� */
	private java.lang.String name;
	/** ������ID */
	private java.math.BigDecimal jobGrpId;
	/** IP��ַ */
	private java.lang.String ipAddr;
	/** ��ʼʱ�� */
	private java.util.Date startTimeBegin;
	private java.util.Date startTimeEnd;
	/** ����ʱ�� */
	private java.util.Date endTimeBegin;
	private java.util.Date endTimeEnd;
	/** ִ��״̬ */
	private java.lang.String status;
	/** ������Ϣ */
	private java.lang.String errMsg;

	public java.math.BigDecimal getId() {
		return this.id;
	}
	
	public void setId(java.math.BigDecimal value) {
		this.id = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.math.BigDecimal getJobGrpId() {
		return this.jobGrpId;
	}
	
	public void setJobGrpId(java.math.BigDecimal value) {
		this.jobGrpId = value;
	}
	
	public java.lang.String getIpAddr() {
		return this.ipAddr;
	}
	
	public void setIpAddr(java.lang.String value) {
		this.ipAddr = value;
	}
	
	public java.util.Date getStartTimeBegin() {
		return this.startTimeBegin;
	}
	
	public void setStartTimeBegin(java.util.Date value) {
		this.startTimeBegin = value;
	}	
	
	public java.util.Date getStartTimeEnd() {
		return this.startTimeEnd;
	}
	
	public void setStartTimeEnd(java.util.Date value) {
		this.startTimeEnd = value;
	}
	
	public java.util.Date getEndTimeBegin() {
		return this.endTimeBegin;
	}
	
	public void setEndTimeBegin(java.util.Date value) {
		this.endTimeBegin = value;
	}	
	
	public java.util.Date getEndTimeEnd() {
		return this.endTimeEnd;
	}
	
	public void setEndTimeEnd(java.util.Date value) {
		this.endTimeEnd = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getErrMsg() {
		return this.errMsg;
	}
	
	public void setErrMsg(java.lang.String value) {
		this.errMsg = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
