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
@Table(name = "BATCH_JOB_GROUP")
public class BatchJobGroup extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "BatchJobGroup";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_JOB_GROUP_DESC = "jobGroupDesc";
	
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
     * jobGroupDesc       db_column: JOB_GROUP_DESC 
     */ 	
	@Length(max=64)
	private java.lang.String jobGroupDesc;
	//columns END


	public BatchJobGroup(){
	}

	public BatchJobGroup(
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
	
	@Column(name = "JOB_GROUP_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getJobGroupDesc() {
		return this.jobGroupDesc;
	}
	
	public void setJobGroupDesc(java.lang.String value) {
		this.jobGroupDesc = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("JobGroupDesc",getJobGroupDesc())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BatchJobGroup == false) return false;
		if(this == obj) return true;
		BatchJobGroup other = (BatchJobGroup)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

