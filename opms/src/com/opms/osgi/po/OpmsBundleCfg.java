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
	public static final String TABLE_ALIAS = "����������ñ�";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BUNDLE_NAME = "�����";
	public static final String ALIAS_BUNDLE_TYPE = "�������";
	public static final String ALIAS_BUNDLE_PATH = "���·��";
	public static final String ALIAS_START_FLAG = "������ʶ";
	public static final String ALIAS_STOP_FLAG = "ֹͣ��ʶ";
	public static final String ALIAS_REFRESH_FLAG = "ˢ�±�ʶ";
	public static final String ALIAS_UPDATE_FLAG = "���±�ʶ";
	public static final String ALIAS_UNINSTALL_FLAG = "ж�ر�ʶ";
	public static final String ALIAS_SYSTEM_ID = "ϵͳ��ʶ";
	
	//date formats
	

	//����ֱ��ʹ��: @Length(max=50,message="�û������Ȳ��ܴ���50")��ʾ������Ϣ
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * �����       db_column: BUNDLE_NAME 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String bundleName;
    /**
     * �������       db_column: BUNDLE_TYPE 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String bundleType;
    /**
     * ���·��       db_column: BUNDLE_PATH 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String bundlePath;
    /**
     * ������ʶ       db_column: START_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String startFlag;
    /**
     * ֹͣ��ʶ       db_column: STOP_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String stopFlag;
    /**
     * ˢ�±�ʶ       db_column: REFRESH_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String refreshFlag;
    /**
     * ���±�ʶ       db_column: UPDATE_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String updateFlag;
    /**
     * ж�ر�ʶ       db_column: UNINSTALL_FLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String uninstallFlag;
    /**
     * ϵͳ��ʶ       db_column: SYSTEM_ID 
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

