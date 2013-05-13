package com.opms.batch.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class BatchStepQuery implements Serializable {
    

	/** id */
	private java.lang.String id;
	/** name */
	private java.lang.String name;
	/** stepDesc */
	private java.lang.String stepDesc;
	/** jobId */
	private java.lang.String jobId;
	/** priority */
	private java.lang.Long priority;
	/** maxThread */
	private java.lang.Long maxThread;
	/** clazz */
	private java.lang.String clazz;

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
	
	public java.lang.String getStepDesc() {
		return this.stepDesc;
	}
	
	public void setStepDesc(java.lang.String value) {
		this.stepDesc = value;
	}
	
	public java.lang.String getJobId() {
		return this.jobId;
	}
	
	public void setJobId(java.lang.String value) {
		this.jobId = value;
	}
	
	public java.lang.Long getPriority() {
		return this.priority;
	}
	
	public void setPriority(java.lang.Long value) {
		this.priority = value;
	}
	
	public java.lang.Long getMaxThread() {
		return this.maxThread;
	}
	
	public void setMaxThread(java.lang.Long value) {
		this.maxThread = value;
	}
	
	public java.lang.String getClazz() {
		return this.clazz;
	}
	
	public void setClazz(java.lang.String value) {
		this.clazz = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

