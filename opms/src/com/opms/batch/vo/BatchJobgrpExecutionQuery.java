package com.opms.batch.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class BatchJobgrpExecutionQuery implements Serializable {
    

	/** PK */
	private java.math.BigDecimal id;
	/** 批量组名称 */
	private java.lang.String name;
	/** 批次号 */
	private java.lang.String batchNo;
	/** IP地址 */
	private java.lang.String ipAddr;
	/** 开始时间 */
	private java.util.Date startTimeBegin;
	private java.util.Date startTimeEnd;
	/** 结束时间 */
	private java.util.Date endTimeBegin;
	private java.util.Date endTimeEnd;
	/** 执行状态 */
	private java.lang.String status;

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
	
	public java.lang.String getBatchNo() {
		return this.batchNo;
	}
	
	public void setBatchNo(java.lang.String value) {
		this.batchNo = value;
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
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

