package com.opms.conmg.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class EaoperateroleQuery implements Serializable {
    

	/** PK */
	private java.lang.String id;
	/** 表名 */
	private java.lang.String tablename;
	/** 插入数据权限标记 */
	private java.lang.String insertflag;
	/** 更新数据权限标记 */
	private java.lang.String updateflag;
	/** 删除数据权限标记 */
	private java.lang.String deleteflag;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getTablename() {
		return this.tablename;
	}
	
	public void setTablename(java.lang.String value) {
		this.tablename = value;
	}
	
	public java.lang.String getInsertflag() {
		return this.insertflag;
	}
	
	public void setInsertflag(java.lang.String value) {
		this.insertflag = value;
	}
	
	public java.lang.String getUpdateflag() {
		return this.updateflag;
	}
	
	public void setUpdateflag(java.lang.String value) {
		this.updateflag = value;
	}
	
	public java.lang.String getDeleteflag() {
		return this.deleteflag;
	}
	
	public void setDeleteflag(java.lang.String value) {
		this.deleteflag = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

