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
@Table(name = "BATCH_STEP")
public class BatchStep extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "BatchStep";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_STEP_DESC = "stepDesc";
	public static final String ALIAS_JOB_ID = "jobId";
	public static final String ALIAS_PRIORITY = "priority";
	public static final String ALIAS_MAX_THREAD = "maxThread";
	public static final String ALIAS_CLAZZ = "clazz";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * id       db_column: ID 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * name       db_column: NAME 
     */ 	
	@NotBlank @Length(max=64)
	private java.lang.String name;
    /**
     * stepDesc       db_column: STEP_DESC 
     */ 	
	@Length(max=32)
	private java.lang.String stepDesc;
    /**
     * jobId       db_column: JOB_ID 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String jobId;
    /**
     * priority       db_column: PRIORITY 
     */ 	
	@NotNull 
	private java.lang.Long priority;
    /**
     * maxThread       db_column: MAX_THREAD 
     */ 	
	
	private java.lang.Long maxThread;
    /**
     * clazz       db_column: CLAZZ 
     */ 	
	@NotBlank @Length(max=255)
	private java.lang.String clazz;
	//columns END


	public BatchStep(){
	}

	public BatchStep(
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
	
	@Column(name = "NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "STEP_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getStepDesc() {
		return this.stepDesc;
	}
	
	public void setStepDesc(java.lang.String value) {
		this.stepDesc = value;
	}
	
	@Column(name = "JOB_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getJobId() {
		return this.jobId;
	}
	
	public void setJobId(java.lang.String value) {
		this.jobId = value;
	}
	
	@Column(name = "PRIORITY", unique = false, nullable = false, insertable = true, updatable = true, length = 38)
	public java.lang.Long getPriority() {
		return this.priority;
	}
	
	public void setPriority(java.lang.Long value) {
		this.priority = value;
	}
	
	@Column(name = "MAX_THREAD", unique = false, nullable = true, insertable = true, updatable = true, length = 38)
	public java.lang.Long getMaxThread() {
		return this.maxThread;
	}
	
	public void setMaxThread(java.lang.Long value) {
		this.maxThread = value;
	}
	
	@Column(name = "CLAZZ", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	public java.lang.String getClazz() {
		return this.clazz;
	}
	
	public void setClazz(java.lang.String value) {
		this.clazz = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("StepDesc",getStepDesc())
			.append("JobId",getJobId())
			.append("Priority",getPriority())
			.append("MaxThread",getMaxThread())
			.append("Clazz",getClazz())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BatchStep == false) return false;
		if(this == obj) return true;
		BatchStep other = (BatchStep)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

