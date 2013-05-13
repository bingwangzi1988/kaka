package com.opms.osgi.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class OpmsBundleCfgQuery implements Serializable {
    

	/** PK */
	private java.lang.String id;
	/** 组件名 */
	private java.lang.String bundleName;
	/** 组件类型 */
	private java.lang.String bundleType;
	/** 组件路径 */
	private java.lang.String bundlePath;
	/** 启动标识 */
	private java.lang.String startFlag;
	/** 停止标识 */
	private java.lang.String stopFlag;
	/** 刷新标识 */
	private java.lang.String refreshFlag;
	/** 更新标识 */
	private java.lang.String updateFlag;
	/** 卸载标识 */
	private java.lang.String uninstallFlag;
	/** 系统标识 */
	private java.lang.String systemId;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getBundleName() {
		return this.bundleName;
	}
	
	public void setBundleName(java.lang.String value) {
		this.bundleName = value;
	}
	
	public java.lang.String getBundleType() {
		return this.bundleType;
	}
	
	public void setBundleType(java.lang.String value) {
		this.bundleType = value;
	}
	
	public java.lang.String getBundlePath() {
		return this.bundlePath;
	}
	
	public void setBundlePath(java.lang.String value) {
		this.bundlePath = value;
	}
	
	public java.lang.String getStartFlag() {
		return this.startFlag;
	}
	
	public void setStartFlag(java.lang.String value) {
		this.startFlag = value;
	}
	
	public java.lang.String getStopFlag() {
		return this.stopFlag;
	}
	
	public void setStopFlag(java.lang.String value) {
		this.stopFlag = value;
	}
	
	public java.lang.String getRefreshFlag() {
		return this.refreshFlag;
	}
	
	public void setRefreshFlag(java.lang.String value) {
		this.refreshFlag = value;
	}
	
	public java.lang.String getUpdateFlag() {
		return this.updateFlag;
	}
	
	public void setUpdateFlag(java.lang.String value) {
		this.updateFlag = value;
	}
	
	public java.lang.String getUninstallFlag() {
		return this.uninstallFlag;
	}
	
	public void setUninstallFlag(java.lang.String value) {
		this.uninstallFlag = value;
	}
	
	public java.lang.String getSystemId() {
		return this.systemId;
	}
	
	public void setSystemId(java.lang.String value) {
		this.systemId = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

