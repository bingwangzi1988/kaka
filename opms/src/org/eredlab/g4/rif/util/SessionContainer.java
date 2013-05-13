package org.eredlab.g4.rif.util;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.rif.report.jasper.ReportData;

/**
 * Session����
 * 
 * @author �ܴ�
 * @since 2009-09-03
 * @see HttpSessionBindingListener
 */
public class SessionContainer implements HttpSessionBindingListener {
	
	/**
	 * ��½�û�����
	 */
	private UserInfoVo userInfo;

	/**
	 * �������
	 */
	private Dto reportDto;
	
	public SessionContainer() {
		super();
		reportDto =  new BaseDto();
	}
	
	/**
	 * ���ñ������
	 * ȱʡ������֧��һ��ҳ��һ����ӡ����
	 * @param pReportData
	 */
	public void setReportData(ReportData pReportData){
		reportDto.put("default", pReportData);
	}
	
	/**
	 * ��ȡ�������
	 * ȱʡ������֧��һ��ҳ��һ����ӡ����
	 * @return
	 */
	public ReportData getReportData(){
		return (ReportData)reportDto.get("default");
	}
	
	/**
	 * ���ñ������
	 * ���ط�����֧��һ��ҳ������ӡ����
	 * @param pReportData
	 */
	public void setReportData(String pFlag, ReportData pReportData){
		reportDto.put(pFlag, pReportData);
	}
	
	/**
	 * ��ȡ�������
	 * ���ط�����֧��һ��ҳ������ӡ����
	 * @return
	 */
	public ReportData getReportData(String pFlag){
		return (ReportData)reportDto.get(pFlag);
	}
	

	/**
	 * ����Ự�����������
	 */
	public void cleanUp() {
		// userInfo�����ڴ���������,ֻ��ʹ��setUserInfo(null)����������ж�������
		// userInfo = null;
		reportDto.clear();
	}

	public void valueBound(HttpSessionBindingEvent event) {
		//System.out.println("�Ự������");
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		//System.out.println("�Ự������");
	}

	/**
	 * ��ȡ�û��Ự����
	 * @return UserInfo
	 */
	public UserInfoVo getUserInfo() {
		return userInfo;
	}

	/**
	 * �����û��Ự����
	 * @param userInfo
	 */
	public void setUserInfo(UserInfoVo userInfo) {
		this.userInfo = userInfo;
		
	}

}
