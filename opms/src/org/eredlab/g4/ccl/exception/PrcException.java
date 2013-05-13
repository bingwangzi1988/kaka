package org.eredlab.g4.ccl.exception;

import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * ���ô洢�����쳣
 * 
 * @author XiongChun
 * @since 2011-01-24
 */
public class PrcException extends RuntimeException {

	private String appCode;
	private String errorMsg;
	private String prcName;

	public PrcException(String errorMsg) {
		super(GlobalConstants.Exception_Head + errorMsg);
		setErrorMsg(errorMsg);
	}

	public PrcException(String prcName, String appCode, String errorMsg) {
		super(GlobalConstants.Exception_Head + "���ô洢����[" + prcName + "]��������,�������Ϊ��[" + appCode + "] ����ԭ��[" + errorMsg
				+ "]");
		setAppCode(appCode);
		setPrcName(prcName);
		setErrorMsg(errorMsg);
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getPrcName() {
		return prcName;
	}

	public void setPrcName(String prcName) {
		this.prcName = prcName;
	}

}
