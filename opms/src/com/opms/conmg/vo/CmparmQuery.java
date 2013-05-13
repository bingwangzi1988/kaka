package com.opms.conmg.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class CmparmQuery implements Serializable {
    

	/** PK */
	private java.lang.String id;
	/** 参数标识 */
	private java.lang.String parmId;
	/** 参数标志 */
	private java.lang.String parmFlag;
	/** 参数名称 */
	private java.lang.String parmName;
	/** 参数描述 */
	private java.lang.String parmDesc;
	/** 参数值 */
	private java.lang.String parmValue;
	/** 备用 */
	private java.lang.String memo;
	/** 备用一 */
	private java.lang.String memo1;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getParmId() {
		return this.parmId;
	}
	
	public void setParmId(java.lang.String value) {
		this.parmId = value;
	}
	
	public java.lang.String getParmFlag() {
		return this.parmFlag;
	}
	
	public void setParmFlag(java.lang.String value) {
		this.parmFlag = value;
	}
	
	public java.lang.String getParmName() {
		return this.parmName;
	}
	
	public void setParmName(java.lang.String value) {
		this.parmName = value;
	}
	
	public java.lang.String getParmDesc() {
		return this.parmDesc;
	}
	
	public void setParmDesc(java.lang.String value) {
		this.parmDesc = value;
	}
	
	public java.lang.String getParmValue() {
		return this.parmValue;
	}
	
	public void setParmValue(java.lang.String value) {
		this.parmValue = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	public java.lang.String getMemo1() {
		return this.memo1;
	}
	
	public void setMemo1(java.lang.String value) {
		this.memo1 = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

