package org.eredlab.g4.rif.report.fcf;

import java.util.HashMap;
import java.util.List;

import org.eredlab.g4.rif.web.BaseAction;

/**
 * FlashReport���ͼ����ʵ��
 * 
 * @author XiongChun
 * @since 2010-07-13
 * @see BaseAction
 */
public class DataSet extends HashMap {
	/**
	 * ��������
	 * @param seriesname
	 */
	public void setSeriesname(String seriesname) {
		this.put("seriesname", seriesname);
	}
	
	/**
	 * ��������ɫ
	 * @param color
	 */
	public void setColor(String color) {
		this.put("color", color);
	}
	
	/**
	 * ���÷�������
	 * @param list
	 */
	public void setData(List list) {
		this.put("dataList", list);
	}
	
	/**
	 * �������͸����<br>
	 * <b>ֻ����������ͼ
	 * @param pAreaAlpha
	 */
	public void setAreaAlpha(String pAreaAlpha){
		this.put("areaAlpha", pAreaAlpha);
	}
	
	/**
	 * �����Ƿ���ʾ����߿�<br>
	 * <b>ֻ����������ͼ
	 * @param pShowAreaBorder
	 */
	public void setShowAreaBorder(Boolean pShowAreaBorder){
		this.put("showAreaBorder", pShowAreaBorder.booleanValue() ? "1" : "0");
	}
	
	/**
	 * ��������߿��ϸ<br>
	 * <b>ֻ����������ͼ
	 * @param pAreaBorderThickness
	 */
	public void setAreaBorderThickness(String pAreaBorderThickness){
		this.put("areaBorderThickness", pAreaBorderThickness);
	}
	
	/**
	 * ��������߿���ɫ<br>
	 * <b>ֻ����������ͼ
	 * @param pAreaBorderThickness
	 */
	public void setAreaBorderColor(String pAreaBorderColor){
		this.put("areaBorderColor", pAreaBorderColor);
	}
	
	/**
	 * �����Ƿ���ʾ����
	 * @param pAreaBorderThickness
	 */
	public void setShowValues(Boolean pShowValues){
		this.put("showValues", pShowValues.booleanValue() ? "1" : "0");
	}
	
	/**
	 * �����Ƿ�Ϊ����<br>
	 * <b>ֻ�����ڽ���ͼ
	 * @param pAreaBorderThickness
	 */
	public void setParentYAxis(Boolean parentYAxis){
		this.put("parentYAxis", parentYAxis.booleanValue() ? "S" : "");
	}
	
	
	
    /**
     * ��ȡ��������
     * @return List
     */
	public List getData() {
		return (List)this.get("dataList");
	}
}
