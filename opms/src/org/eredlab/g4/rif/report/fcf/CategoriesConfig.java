package org.eredlab.g4.rif.report.fcf;

import java.util.HashMap;
import java.util.List;

import org.eredlab.g4.rif.web.BaseAction;

/**
 * FlashReport���ͼ�������ö���
 * <b>ֻ���������ͼ
 * @author XiongChun
 * @since 2010-07-13
 * @see BaseAction
 */
public class CategoriesConfig extends HashMap{
	/**
	 * ȱʡ��������
	 */
	public CategoriesConfig(){
		this.put("font", "����");
		this.put("fontSize", "12");
	}
	
	/**
	 * ��������
	 * @param pFoneName
	 */
	public void setFont(String pFoneName){
		this.put("font", pFoneName);
	}
	
	/**
	 * ���������С
	 * @param pFoneName
	 */
	public void setFontSize(String pFontSize){
		this.put("fontSize", pFontSize);
	}
	
	/**
	 * ����������ɫ
	 * @param pFoneName
	 */
	public void setFontColor(String pFontColor){
		this.put("fontColor", pFontColor);
	}
	
	/**
	 * ��������
	 * @param pCategoriesList
	 */
	public void setCategories(List pCategoriesList){
		this.put("categories", pCategoriesList);
	}
	
    /**
     * ��ȡ����
     * @return
     */
	public List getCategories(){
		return (List)this.get("categories");
	}
}
