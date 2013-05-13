package com.opms.batch.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class BatchJobGroupQuery implements Serializable {
    

	/** id */
	private java.lang.String id;
	/** name */
	private java.lang.String name;
	/** jobGroupDesc */
	private java.lang.String jobGroupDesc;

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
	
	public java.lang.String getJobGroupDesc() {
		return this.jobGroupDesc;
	}
	
	public void setJobGroupDesc(java.lang.String value) {
		this.jobGroupDesc = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

