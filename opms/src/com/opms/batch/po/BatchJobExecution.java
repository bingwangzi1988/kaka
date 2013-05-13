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
@Table(name = "BATCH_JOB_EXECUTION")
public class BatchJobExecution extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "BatchJobExecution";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_NAME = "������ҵ����";
	public static final String ALIAS_JOB_GRP_ID = "������ID";
	public static final String ALIAS_IP_ADDR = "IP��ַ";
	public static final String ALIAS_START_TIME = "��ʼʱ��";
	public static final String ALIAS_END_TIME = "����ʱ��";
	public static final String ALIAS_STATUS = "ִ��״̬";
	public static final String ALIAS_ERR_MSG = "������Ϣ";
	
	//date formats
	public static final String FORMAT_START_TIME = DATE_FORMAT;
	public static final String FORMAT_END_TIME = DATE_FORMAT;
	

	//����ֱ��ʹ��: @Length(max=50,message="�û������Ȳ��ܴ���50")��ʾ������Ϣ
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	
	private java.math.BigDecimal id;
    /**
     * ������ҵ����       db_column: NAME 
     */ 	
	@NotBlank @Length(max=64)
	private java.lang.String name;
    /**
     * ������ID       db_column: JOB_GRP_ID 
     */ 	
	@NotNull 
	private java.math.BigDecimal jobGrpId;
    /**
     * IP��ַ       db_column: IP_ADDR 
     */ 	
	@Length(max=38)
	private java.lang.String ipAddr;
    /**
     * ��ʼʱ��       db_column: START_TIME 
     */ 	
	@NotNull 
	private java.util.Date startTime;
    /**
     * ����ʱ��       db_column: END_TIME 
     */ 	
	
	private java.util.Date endTime;
    /**
     * ִ��״̬       db_column: STATUS 
     */ 	
	@NotBlank @Length(max=2)
	private java.lang.String status;
    /**
     * ������Ϣ       db_column: ERR_MSG 
     */ 	
	@Length(max=255)
	private java.lang.String errMsg;
	//columns END


	public BatchJobExecution(){
	}

	public BatchJobExecution(
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
	
	@Column(name = "JOB_GRP_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 22)
	public java.math.BigDecimal getJobGrpId() {
		return this.jobGrpId;
	}
	
	public void setJobGrpId(java.math.BigDecimal value) {
		this.jobGrpId = value;
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
			.append("JobGrpId",getJobGrpId())
			.append("IpAddr",getIpAddr())
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
		if(obj instanceof BatchJobExecution == false) return false;
		if(this == obj) return true;
		BatchJobExecution other = (BatchJobExecution)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

