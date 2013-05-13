package com.opms.batch.po;

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
@Table(name = "BATCH_JOBGRP_EXECUTION")
public class BatchJobgrpExecution extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "BatchJobgrpExecution";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_NAME = "批量组名称";
	public static final String ALIAS_BATCH_NO = "批次号";
	public static final String ALIAS_IP_ADDR = "IP地址";
	public static final String ALIAS_START_TIME = "开始时间";
	public static final String ALIAS_END_TIME = "结束时间";
	public static final String ALIAS_STATUS = "执行状态";
	
	//date formats
	public static final String FORMAT_START_TIME = DATE_FORMAT;
	public static final String FORMAT_END_TIME = DATE_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	
	private java.math.BigDecimal id;
    /**
     * 批量组名称       db_column: NAME 
     */ 	
	@NotBlank @Length(max=64)
	private java.lang.String name;
    /**
     * 批次号       db_column: BATCH_NO 
     */ 	
	@NotBlank @Length(max=28)
	private java.lang.String batchNo;
    /**
     * IP地址       db_column: IP_ADDR 
     */ 	
	@Length(max=38)
	private java.lang.String ipAddr;
    /**
     * 开始时间       db_column: START_TIME 
     */ 	
	@NotNull 
	private java.util.Date startTime;
    /**
     * 结束时间       db_column: END_TIME 
     */ 	
	
	private java.util.Date endTime;
    /**
     * 执行状态       db_column: STATUS 
     */ 	
	@NotBlank @Length(max=2)
	private java.lang.String status;
	//columns END


	public BatchJobgrpExecution(){
	}

	public BatchJobgrpExecution(
		java.math.BigDecimal id
	){
		this.id = id;
	}

	

	public void setId(java.math.BigDecimal value) {
		this.id = value;
	}
	
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid")
	@Column(name = "ID", unique = false, nullable = false, insertable = true, updatable = true, length = 22)
	public java.math.BigDecimal getId() {
		return this.id;
	}
	
	@Column(name = "NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "BATCH_NO", unique = false, nullable = false, insertable = true, updatable = true, length = 28)
	public java.lang.String getBatchNo() {
		return this.batchNo;
	}
	
	public void setBatchNo(java.lang.String value) {
		this.batchNo = value;
	}
	
	@Column(name = "IP_ADDR", unique = false, nullable = true, insertable = true, updatable = true, length = 38)
	public java.lang.String getIpAddr() {
		return this.ipAddr;
	}
	
	public void setIpAddr(java.lang.String value) {
		this.ipAddr = value;
	}
	
	@Transient
	public String getStartTimeString() {
		return DateConvertUtils.format(getStartTime(), FORMAT_START_TIME);
	}
	public void setStartTimeString(String value) {
		setStartTime(DateConvertUtils.parse(value, FORMAT_START_TIME,java.util.Date.class));
	}
	
	@Column(name = "START_TIME", unique = false, nullable = false, insertable = true, updatable = true, length = 7)
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	@Transient
	public String getEndTimeString() {
		return DateConvertUtils.format(getEndTime(), FORMAT_END_TIME);
	}
	public void setEndTimeString(String value) {
		setEndTime(DateConvertUtils.parse(value, FORMAT_END_TIME,java.util.Date.class));
	}
	
	@Column(name = "END_TIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	@Column(name = "STATUS", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("BatchNo",getBatchNo())
			.append("IpAddr",getIpAddr())
			.append("StartTime",getStartTime())
			.append("EndTime",getEndTime())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BatchJobgrpExecution == false) return false;
		if(this == obj) return true;
		BatchJobgrpExecution other = (BatchJobgrpExecution)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

