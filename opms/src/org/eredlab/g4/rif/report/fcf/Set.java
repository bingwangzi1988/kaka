package org.eredlab.g4.rif.report.fcf;

import java.util.HashMap;

import org.eredlab.g4.rif.web.BaseAction;

/**
 * FlashReport����Ԫ����
 * 
 * @author XiongChun
 * @since 2010-07-04
 * @see BaseAction
 */
public class Set extends HashMap {
	/**
	 * ������������
	 * @param pName
	 */
	public void setName(String pName) {
		this.put("name", pName);
	}
	
	/**
	 * ����ֵ����
	 * @param pName
	 */
	public void setValue(String pValue) {
		this.put("value", pValue);
	}
	
	/**
	 * ������ɫ����
	 * @param pName
	 */
	public void setColor(String pColor) {
		this.put("color", pColor);
	}
	
	/**
	 * ���ø�������<br>
	 * <b>ֻ���2D��ͼ��Ч
	 * @param pName
	 */
	public void setIsSliced(String pIsSliced) {
		this.put("isSliced", pIsSliced);
	}
	
	/**
	 * ���������ͣʱ��׷�ӵ��ı�
	 * @param pName
	 */
	public void setHoverText(String pHoverText) {
		this.put("hoverText", pHoverText);
	}
	
	/**
	 * ����͸����
	 * ֻ��©��ͼ��Ч
	 * @param pAlpha
	 */
	public void setAlpha(String pAlpha) {
		this.put("alpha", pAlpha);
	}
	
	/**
	 * ���ú�ͼ�ν�����JS����
	 * �����е���α���ʹ��˫���Ų���ʹ�õ�����
	 * @param pFunction
	 */
	public void setJsFunction(String pFunction) {
		this.put("link", "JavaScript:" + pFunction + ";");
	}
	
	/**
	 * ���ú�ͼ�ε���������
	 * @param pLink
	 */
	public void setLink(String pLink) {
		this.put("link", "n-" + pLink);
	}
}
