package org.eredlab.g4.rif.report.jasper;

import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ��������ģ��
 * @author XiongChun
 * @since 2009-09-03
 */
public class ReportData {
	/**
	 * �����ļ�·��
	 */
	private String reportFilePath;

	/**
	 * �����������
	 */
	private Dto parametersDto;

	/**
	 * �����϶���
	 */
	private List fieldsList;
	
	public ReportData() {}
	
	/**
	 * ���캯��
	 * 
	 * @param pReportFilePath �����ļ�·��
	 * @param pParametersDto ���������
	 * @param pFieldsList �����ֶ��б���
	 */
	public ReportData(String pReportFilePath, Dto pParametersDto, List pFieldsList) {
		reportFilePath = pReportFilePath;
		parametersDto = pParametersDto;
		fieldsList = pFieldsList;
	}
    
	public String getReportFilePath() {
		return reportFilePath;
	}

	/**
	 * ���ñ����ļ�·��
	 * @param reportFilePath
	 */
	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public Dto getParametersDto() {
		return parametersDto;
	}

	/**
	 * ���ñ��������
	 * @param parametersDto
	 */
	public void setParametersDto(Dto parametersDto) {
		this.parametersDto = parametersDto;
	}

	public List getFieldsList() {
		return fieldsList;
	}

	/**
	 * ���ñ����ֶ��б���
	 * @param fieldsList
	 */
	public void setFieldsList(List fieldsList) {
		this.fieldsList = fieldsList;
	}

}
