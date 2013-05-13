package com.opms.conmg.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


public class EaoperatercdQuery implements Serializable {
    

	/** id */
	private java.lang.String id;
	/** ����Ա���� */
	private java.lang.String userid;
	/** ��ȨԱ���� */
	private java.lang.String authorizerid;
	/** ��Ȩʱ�� */
	private java.util.Date operatedateBegin;
	private java.util.Date operatedateEnd;
	/** �������� */
	private java.lang.String operatetype;
	/** �������� */
	private java.lang.String operateobject;
	/** ������Ϣ */
	private java.lang.String operateinfo;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getUserid() {
		return this.userid;
	}
	
	public void setUserid(java.lang.String value) {
		this.userid = value;
	}
	
	public java.lang.String getAuthorizerid() {
		return this.authorizerid;
	}
	
	public void setAuthorizerid(java.lang.String value) {
		this.authorizerid = value;
	}
	
	public java.util.Date getOperatedateBegin() {
		return this.operatedateBegin;
	}
	
	public void setOperatedateBegin(java.util.Date value) {
		this.operatedateBegin = value;
	}	
	
	public java.util.Date getOperatedateEnd() {
		return this.operatedateEnd;
	}
	
	public void setOperatedateEnd(java.util.Date value) {
		this.operatedateEnd = value;
	}
	
	public java.lang.String getOperatetype() {
		return this.operatetype;
	}
	
	public void setOperatetype(java.lang.String value) {
		this.operatetype = value;
	}
	
	public java.lang.String getOperateobject() {
		return this.operateobject;
	}
	
	public void setOperateobject(java.lang.String value) {
		this.operateobject = value;
	}
	
	public java.lang.String getOperateinfo() {
		return this.operateinfo;
	}
	
	public void setOperateinfo(java.lang.String value) {
		this.operateinfo = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

