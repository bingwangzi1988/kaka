package com.opms.batch.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class BatchJobQuery implements Serializable {
    

	/** id */
	private java.lang.String id;
	/** name */
	private java.lang.String name;
	/** jobDesc */
	private java.lang.String jobDesc;
	/** jobGroupId */
	private java.lang.String jobGroupId;
	/** priority */
	private java.lang.Long priority;
	/** hostName */
	private java.lang.String hostName;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getJobDesc() {
		return this.jobDesc;
	}
	
	public void setJobDesc(java.lang.String value) {
		this.jobDesc = value;
	}
	
	public java.lang.String getJobGroupId() {
		return this.jobGroupId;
	}
	
	public void setJobGroupId(java.lang.String value) {
		this.jobGroupId = value;
	}
	
	public java.lang.Long getPriority() {
		return this.priority;
	}
	
	public void setPriority(java.lang.Long value) {
		this.priority = value;
	}
	
	public java.lang.String getHostName() {
		return this.hostName;
	}
	
	public void setHostName(java.lang.String value) {
		this.hostName = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

