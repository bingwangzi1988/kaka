package org.eredlab.g4.rif.report.fcf;

import java.util.HashMap;

import org.eredlab.g4.rif.web.BaseAction;

/**
 * FlashReport����Ԫ����<br>
 * <b>ֻ���������ͼ
 * @author XiongChun
 * @since 2010-07-04
 * @see BaseAction
 */
public class Categorie extends HashMap {
	
	public Categorie(String pName){
		setName(pName);
	}
	
	/**
	 * ������������
	 * @param pName
	 */
	public void setName(String pName) {
		this.put("name", pName);
	}
	
	/**
	 * ���������ͣʱ��׷�ӵ��ı�
	 * @param pName
	 */
	public void setHoverText(String pHoverText) {
		this.put("hoverText", pHoverText);
	}
}
