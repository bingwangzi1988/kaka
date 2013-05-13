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
@Table(name = "BATCH_JOB")
public class BatchJob extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "BatchJob";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_JOB_DESC = "jobDesc";
	public static final String ALIAS_JOB_GROUP_ID = "jobGroupId";
	public static final String ALIAS_PRIORITY = "priority";
	public static final String ALIAS_HOST_NAME = "hostName";
	
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
	@NotBlank @Length(max=32)
	private java.lang.String name;
    /**
     * jobDesc       db_column: JOB_DESC 
     */ 	
	@Length(max=64)
	private java.lang.String jobDesc;
    /**
     * jobGroupId       db_column: JOB_GROUP_ID 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String jobGroupId;
    /**
     * priority       db_column: PRIORITY 
     */ 	
	@NotNull 
	private java.lang.Long priority;
    /**
     * hostName       db_column: HOST_NAME 
     */ 	
	@Length(max=19)
	private java.lang.String hostName;
	//columns END


	public BatchJob(){
	}

	public BatchJob(
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
	
	@Column(name = "NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "JOB_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getJobDesc() {
		return this.jobDesc;
	}
	
	public void setJobDesc(java.lang.String value) {
		this.jobDesc = value;
	}
	
	@Column(name = "JOB_GROUP_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getJobGroupId() {
		return this.jobGroupId;
	}
	
	public void setJobGroupId(java.lang.String value) {
		this.jobGroupId = value;
	}
	
	@Column(name = "PRIORITY", unique = false, nullable = false, insertable = true, updatable = true, length = 38)
	public java.lang.Long getPriority() {
		return this.priority;
	}
	
	public void setPriority(java.lang.Long value) {
		this.priority = value;
	}
	
	@Column(name = "HOST_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getHostName() {
		return this.hostName;
	}
	
	public void setHostName(java.lang.String value) {
		this.hostName = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("JobDesc",getJobDesc())
			.append("JobGroupId",getJobGroupId())
			.append("Priority",getPriority())
			.append("HostName",getHostName())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BatchJob == false) return false;
		if(this == obj) return true;
		BatchJob other = (BatchJob)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

