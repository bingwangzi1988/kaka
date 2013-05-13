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
@Table(name = "BATCH_STEP_EXECUTION")
public class BatchStepExecution extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "BatchStepExecution";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_NAME = "批量任务名称";
	public static final String ALIAS_JOB_ID = "批量作业ID";
	public static final String ALIAS_START_TIME = "开始时间";
	public static final String ALIAS_END_TIME = "结束时间";
	public static final String ALIAS_STATUS = "执行状态";
	public static final String ALIAS_ERR_MSG = "错误信息";
	
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
     * 批量任务名称       db_column: NAME 
     */ 	
	@NotBlank @Length(max=64)
	private java.lang.String name;
    /**
     * 批量作业ID       db_column: JOB_ID 
     */ 	
	@NotNull 
	private java.math.BigDecimal jobId;
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
    /**
     * 错误信息       db_column: ERR_MSG 
     */ 	
	@Length(max=255)
	private java.lang.String errMsg;
	//columns END


	public BatchStepExecution(){
	}

	public BatchStepExecution(
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
	
	@Column(name = "JOB_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 22)
	public java.math.BigDecimal getJobId() {
		return this.jobId;
	}
	
	public void setJobId(java.math.BigDecimal value) {
		this.jobId = value;
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
	
	@Column(name = "ERR_MSG", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public java.lang.String getErrMsg() {
		return this.errMsg;
	}
	
	public void setErrMsg(java.lang.String value) {
		this.errMsg = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("JobId",getJobId())
			.append("StartTime",getStartTime())
			.append("EndTime",getEndTime())
			.append("Status",getStatus())
			.append("ErrMsg",getErrMsg())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BatchStepExecution == false) return false;
		if(this == obj) return true;
		BatchStepExecution other = (BatchStepExecution)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

