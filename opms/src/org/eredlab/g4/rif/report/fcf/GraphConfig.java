package org.eredlab.g4.rif.report.fcf;

import java.util.HashMap;

import org.eredlab.g4.rif.web.BaseAction;

/**
 * FlashReport��ʽ���ö���
 * @author XiongChun
 * @since 2010-07-13
 * @see BaseAction
 */
public class GraphConfig extends HashMap {
	public GraphConfig(){
		this.put("baseFont", "����");
		this.put("baseFontSize", "12");
		this.put("canvasBorderThickness", "1");
		
	}
	
	/**
	 * ����������
	 * @param pCaption
	 */
	public void setCaption(String pCaption){
		this.put("caption", pCaption);
	}
	
	/**
	 * ���ø�����
	 * @param pSubcaption
	 */
	public void setSubcaption(String pSubcaption){
		this.put("subcaption", pSubcaption);
	}
	
	/**
	 * ����X����������
	 * @param pXAxisName
	 */
	public void setXAxisName(String pXAxisName){
		this.put("xAxisName", pXAxisName);
	}
	
	/**
	 * ����Y����������
	 * @param pYAxisName
	 */
	public void setYAxisName(String pYAxisName){
		this.put("yAxisName", pYAxisName);
	}
	
	/**
	 * ��������
	 * @param pBaseFont
	 */
	public void setBaseFont(String pBaseFont){
		this.put("baseFont", pBaseFont);
	}
	
	/**
	 * ���������С
	 * @param pBaseFontSize
	 */
	public void setBaseFontSize(String pBaseFontSize){
		this.put("baseFontSize", pBaseFontSize);
	}
	
	/**
	 * ��������ֵ��ǰ׺
	 * @param pNumberPrefix
	 */
	public void setNumberPrefix(String pNumberPrefix){
		this.put("numberPrefix", pNumberPrefix);
	}
	
	/**
	 * ����ϸ�߿�
	 * @param pCanvasBorderThickness
	 */
	public void setCanvasBorderThickness(Boolean pCanvasBorderThickness){
		this.put("canvasBorderThickness", pCanvasBorderThickness.booleanValue() ? "1" : "0");
	}
	
	/**
	 * ���ñ߿���ɫ
	 * @param pCanvasBorderColor
	 */
	public void setCanvasBorderColor(String pCanvasBorderColor){
		this.put("CanvasBorderColor", pCanvasBorderColor);
	}
	
	/**
	 * �����Ƿ�ͼ����ʾ��������
	 * @param pCanvasBorderColor
	 */
	public void setShowNames(Boolean pShowNames){
		this.put("showNames", pShowNames.booleanValue() ? "1" : "0");
	}
	
	/**
	 * �����Ƿ���ʾ����
	 * @param pAreaBorderThickness
	 */
	public void setShowValues(Boolean pShowValues){
		this.put("showValues", pShowValues.booleanValue() ? "1" : "0");
	}
}
