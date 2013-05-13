package org.eredlab.g4.ccl.datastructure.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.assistant.TypeCaseHelper;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.ccl.xml.XmlHelper;

/**
 * ���ݴ������(DateTransferObject)<br>
 * �����ڲ������ݹ����о���ʹ��Dto������<br>
 * 
 * @author XiongChun
 * @since 2009-06-23
 * @see Dto
 * @see java.io.Serializable
 */
public class BaseDto extends HashMap implements Dto, Serializable {
	
	public BaseDto(){}
	
	public BaseDto(String key, Object value){
		put(key, value);
	}

	/**
	 * ��BigDecimal���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return BigDecimal ��ֵ
	 */
	public BigDecimal getAsBigDecimal(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "BigDecimal", null);
		if (obj != null)
			return (BigDecimal) obj;
		else
			return null;
	}

	/**
	 * ��Date���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return Date ��ֵ
	 */
	public Date getAsDate(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Date", "yyyy-MM-dd");
		if (obj != null)
			return (Date) obj;
		else
			return null;
	}

	/**
	 * ��Integer���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return Integer ��ֵ
	 */
	public Integer getAsInteger(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Integer", null);
		if (obj != null)
			return (Integer) obj;
		else
			return null;
	}

	/**
	 * ��Long���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return Long ��ֵ
	 */
	public Long getAsLong(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Long", null);
		if (obj != null)
			return (Long) obj;
		else
			return null;
	}

	/**
	 * ��String���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return String ��ֵ
	 */
	public String getAsString(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "String", null);
		if (obj != null)
			return (String) obj;
		else
			return "";
	}
	
	/**
	 * ��List���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return List ��ֵ
	 */
	public List getAsList(String key){
		return (List)get(key);
	}

	/**
	 * ��Timestamp���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return Timestamp ��ֵ
	 */
	public Timestamp getAsTimestamp(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Timestamp", "yyyy-MM-dd HH:mm:ss");
		if (obj != null)
			return (Timestamp) obj;
		else
			return null;
	}

	/**
	 * ��Dtoѹ���һ��Ĭ��List����<br>
	 * Ϊ�˷����ȡ(ʡȥ����Key����ȡ������ת���Ĺ���)
	 * 
	 * @param pList
	 *            ѹ��Dto��List����
	 */
	public void setDefaultAList(List pList) {
		put("defaultAList", pList);
	}

	/**
	 * ��Dtoѹ��ڶ���Ĭ��List����<br>
	 * Ϊ�˷����ȡ(ʡȥ����Key����ȡ������ת���Ĺ���)
	 * 
	 * @param pList
	 *            ѹ��Dto��List����
	 */
	public void setDefaultBList(List pList) {
		put("defaultBList", pList);
	}

	/**
	 * ��ȡ��һ��Ĭ��List����<br>
	 * Ϊ�˷����ȡ(ʡȥ����Key����ȡ������ת���Ĺ���)
	 * 
	 * @param pList
	 *            ѹ��Dto��List����
	 */
	public List getDefaultAList() {
		return (List) get("defaultAList");
	}

	/**
	 * ��ȡ�ڶ���Ĭ��List����<br>
	 * Ϊ�˷����ȡ(ʡȥ����Key����ȡ������ת���Ĺ���)
	 * 
	 * @param pList
	 *            ѹ��Dto��List����
	 */
	public List getDefaultBList() {
		return (List) get("defaultBList");
	}
	
    /**
     * ��Dtoѹ��һ��Ĭ�ϵ�Json��ʽ�ַ���
     * @param jsonString
     */
    public void setDefaultJson(String jsonString){
    	put("defaultJsonString", jsonString);
    }
    
    /**
     * ��ȡĬ�ϵ�Json��ʽ�ַ���
     * @return
     */
    public String getDefaultJson(){
    	return getAsString("defaultJsonString");
    }

	/**
	 * ����Dto����ת��ΪXML��ʽ�ַ���
	 * 
	 * @param pStyle
	 *            XML���ɷ�ʽ(��ѡ���ڵ�����ֵ���ͽڵ�Ԫ��ֵ���)
	 * @return string ����XML��ʽ�ַ���
	 */
	public String toXml(String pStyle) {
		String strXml = null;
		if (pStyle.equals(GlobalConstants.XML_Attribute))
			// �ڵ�����ֵ���
			strXml = XmlHelper.parseDto2Xml(this, "root", "row");
		else if (pStyle.equals(GlobalConstants.XML_Node))
			// �ڵ�Ԫ��ֵ���
			strXml = XmlHelper.parseDto2Xml(this, "root");
		return strXml;
	}

	/**
	 * ����Dto����ת��ΪXML��ʽ�ַ���<br>
	 * Ĭ��Ϊ�ڵ�Ԫ��ֵ���
	 * 
	 * @return string ����XML��ʽ�ַ���
	 */
	public String toXml() {
		String strXml = null;
		// �ڵ�Ԫ��ֵ���
		strXml = XmlHelper.parseDto2Xml(this, "root");
		return strXml;
	}
	
	/**
	 * ����Dto����ת��ΪJson��ʽ�ַ���<br>
	 * 
	 * @return string ����Json��ʽ�ַ���
	 */
	public String toJson() {
		String strJson = null;
		strJson = JsonHelper.encodeObject2Json(this);
		return strJson;
	}
	
	/**
	 * ����Dto����ת��ΪJson��ʽ�ַ���(������ʱ����)<br>
	 * 
	 * @return string ����Json��ʽ�ַ���
	 */
	public String toJson(String pFormat){
		String strJson = null;
		strJson = JsonHelper.encodeObject2Json(this, pFormat);
		return strJson;
	}
	
	/**
	 * �洢���̷��ش���ֵ<br>
	 * ��SQLMAP�ж���ĳ����ֶα��붨��ΪappCode
	 * 
	 * @return
	 */
	public String getAppCode() {
		return getAsString("appCode");
	}
    
	/**
	 * ���ô洢���̵Ĵ�����Ϣ<br>
	 * ��SQLMAP�ж���ĳ����ֶα��붨��ΪerrMsg
	 * 
	 * @return
	 */
	public String getErrorMsg() {
		return getAsString("errMsg");
	}
}
