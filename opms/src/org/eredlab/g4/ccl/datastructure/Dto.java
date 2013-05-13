package org.eredlab.g4.ccl.datastructure;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * ���ݴ������ӿ�<br>
 * 
 * @author XiongChun
 * @since 2009-07-06
 * @see java.util.Map
 */
public interface Dto extends Map {

	/**
	 * ��Integer���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return Integer ��ֵ
	 */
	public Integer getAsInteger(String pStr);

	/**
	 * ��Long���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return Long ��ֵ
	 */
	public Long getAsLong(String pStr);

	/**
	 * ��String���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return String ��ֵ
	 */
	public String getAsString(String pStr);

	/**
	 * ȡ������ֵ
	 * 
	 * @param pStr
	 *            ����Key
	 * @return Integer
	 */
	public BigDecimal getAsBigDecimal(String pStr);

	/**
	 * ȡ������ֵ
	 * 
	 * @param pStr
	 *            :����Key
	 * @return Integer
	 */
	public Date getAsDate(String pStr);

	/**
	 * ��List���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return List ��ֵ
	 */
	public List getAsList(String key);

	/**
	 * ��Timestamp���ͷ��ؼ�ֵ
	 * 
	 * @param key
	 *            ����
	 * @return Timestamp ��ֵ
	 */
	public Timestamp getAsTimestamp(String pStr);

	/**
	 * ��Dtoѹ���һ��Ĭ��List����<br>
	 * Ϊ�˷����ȡ(ʡȥ����Key����ȡ������ת���Ĺ���)
	 * 
	 * @param pList
	 *            ѹ��Dto��List����
	 */
	public void setDefaultAList(List pList);

	/**
	 * ��Dtoѹ��ڶ���Ĭ��List����<br>
	 * Ϊ�˷����ȡ(ʡȥ����Key����ȡ������ת���Ĺ���)
	 * 
	 * @param pList
	 *            ѹ��Dto��List����
	 */
	public void setDefaultBList(List pList);

	/**
	 * ��ȡ��һ��Ĭ��List����<br>
	 * Ϊ�˷����ȡ(ʡȥ����Key����ȡ������ת���Ĺ���)
	 * 
	 * @param pList
	 *            ѹ��Dto��List����
	 */
	public List getDefaultAList();

	/**
	 * ��ȡ�ڶ���Ĭ��List����<br>
	 * Ϊ�˷����ȡ(ʡȥ����Key����ȡ������ת���Ĺ���)
	 * 
	 * @param pList
	 *            ѹ��Dto��List����
	 */
	public List getDefaultBList();

	/**
	 * ��Dtoѹ��һ��Ĭ�ϵ�Json��ʽ�ַ���
	 * 
	 * @param jsonString
	 */
	public void setDefaultJson(String jsonString);

	/**
	 * ��ȡĬ�ϵ�Json��ʽ�ַ���
	 * 
	 * @return
	 */
	public String getDefaultJson();

	/**
	 * ����Dto����ת��ΪXML��ʽ�ַ���
	 * 
	 * @param pStyle
	 *            XML���ɷ�ʽ(��ѡ���ڵ�����ֵ���ͽڵ�Ԫ��ֵ���)
	 * @return string ����XML��ʽ�ַ���
	 */
	public String toXml(String pStyle);

	/**
	 * ����Dto����ת��ΪXML��ʽ�ַ���<br>
	 * Ĭ��Ϊ�ڵ�Ԫ��ֵ���
	 * 
	 * @return string ����XML��ʽ�ַ���
	 */
	public String toXml();

	/**
	 * ����Dto����ת��ΪJson��ʽ�ַ���<br>
	 * 
	 * @return string ����Json��ʽ�ַ���
	 */
	public String toJson();
	
	/**
	 * ����Dto����ת��ΪJson��ʽ�ַ���(������ʱ����)<br>
	 * 
	 * @return string ����Json��ʽ�ַ���
	 */
	public String toJson(String pFormat);

	/**
	 * �洢���̷��ش���ֵ<br>
	 * ��SQLMAP�ж���ĳ����ֶα��붨��ΪappCode
	 * 
	 * @return
	 */
	public String getAppCode();

	/**
	 * ���ô洢���̵Ĵ�����Ϣ<br>
	 * ��SQLMAP�ж���ĳ����ֶα��붨��ΪerrMsg
	 * 
	 * @return
	 */
	public String getErrorMsg();
}
