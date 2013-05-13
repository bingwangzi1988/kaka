package com.opms.osgi.po;

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
@Table(name = "OPMS_BUNDLE_CFG")
public class OpmsBundleCfg extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "组件管理配置表";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BUNDLE_NAME = "组件名";
	public static final String ALIAS_BUNDLE_TYPE = "组件类型";
	public static final String ALIAS_BUNDLE_PATH = "组件路径";
	public static final String ALIAS_START_FLAG = "启动标识";
	public static final String ALIAS_STOP_FLAG = "停止标识";
	public static final String ALIAS_REFRESH_FLAG = "刷新标识";
	public static final String ALIAS_UPDATE_FLAG = "更新标识";
	public static final String ALIAS_UNINSTALL_FLAG = "卸载标识";
	public static final String ALIAS_SYSTEM_ID = "系统标识";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 组件名       db_column: BUNDLE_NAME 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String bundleName;
    /**
     * 组件类型       db_column: BUNDLE_TYPE 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String bundleType;
    /**
     * 组件路径       db_column: BUNDLE_PATH 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String bundlePath;
    /**
     * 启动标识       db_column: START_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String startFlag;
    /**
     * 停止标识       db_column: STOP_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String stopFlag;
    /**
     * 刷新标识       db_column: REFRESH_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String refreshFlag;
    /**
     * 更新标识       db_column: UPDATE_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String updateFlag;
    /**
     * 卸载标识       db_column: UNINSTALL_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String uninstallFlag;
    /**
     * 系统标识       db_column: SYSTEM_ID 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String systemId;
	//columns END


	public OpmsBundleCfg(){
	}

	public OpmsBundleCfg(
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
	
	@Column(name = "BUNDLE_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getBundleName() {
		return this.bundleName;
	}
	
	public void setBundleName(java.lang.String value) {
		this.bundleName = value;
	}
	
	@Column(name = "BUNDLE_TYPE", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getBundleType() {
		return this.bundleType;
	}
	
	public void setBundleType(java.lang.String value) {
		this.bundleType = value;
	}
	
	@Column(name = "BUNDLE_PATH", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getBundlePath() {
		return this.bundlePath;
	}
	
	public void setBundlePath(java.lang.String value) {
		this.bundlePath = value;
	}
	
	@Column(name = "START_FLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getStartFlag() {
		return this.startFlag;
	}
	
	public void setStartFlag(java.lang.String value) {
		this.startFlag = value;
	}
	
	@Column(name = "STOP_FLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getStopFlag() {
		return this.stopFlag;
	}
	
	public void setStopFlag(java.lang.String value) {
		this.stopFlag = value;
	}
	
	@Column(name = "REFRESH_FLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getRefreshFlag() {
		return this.refreshFlag;
	}
	
	public void setRefreshFlag(java.lang.String value) {
		this.refreshFlag = value;
	}
	
	@Column(name = "UPDATE_FLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getUpdateFlag() {
		return this.updateFlag;
	}
	
	public void setUpdateFlag(java.lang.String value) {
		this.updateFlag = value;
	}
	
	@Column(name = "UNINSTALL_FLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getUninstallFlag() {
		return this.uninstallFlag;
	}
	
	public void setUninstallFlag(java.lang.String value) {
		this.uninstallFlag = value;
	}
	
	@Column(name = "SYSTEM_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getSystemId() {
		return this.systemId;
	}
	
	public void setSystemId(java.lang.String value) {
		this.systemId = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("BundleName",getBundleName())
			.append("BundleType",getBundleType())
			.append("BundlePath",getBundlePath())
			.append("StartFlag",getStartFlag())
			.append("StopFlag",getStopFlag())
			.append("RefreshFlag",getRefreshFlag())
			.append("UpdateFlag",getUpdateFlag())
			.append("UninstallFlag",getUninstallFlag())
			.append("SystemId",getSystemId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OpmsBundleCfg == false) return false;
		if(this == obj) return true;
		OpmsBundleCfg other = (OpmsBundleCfg)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

