package org.eredlab.g4.rif.report.excel;

import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * Excel���ݶ���
 * 
 * @author XiongChun
 * @since 2010-08-12
 */
public class ExcelData {

	/**
	 * Excel����Ԫ���ݶ���
	 */
	private Dto parametersDto;

	/**
	 * Excel����Ԫ����
	 */
	private List fieldsList;

	/**
	 * ���캯��
	 * 
	 * @param pDto
	 *            Ԫ��������
	 * @param pList
	 *            ����Ԫ����
	 */
	public ExcelData(Dto pDto, List pList) {
		setParametersDto(pDto);
		setFieldsList(pList);
	}

	public Dto getParametersDto() {
		return parametersDto;
	}

	public void setParametersDto(Dto parametersDto) {
		this.parametersDto = parametersDto;
	}

	public List getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(List fieldsList) {
		this.fieldsList = fieldsList;
	}

}
