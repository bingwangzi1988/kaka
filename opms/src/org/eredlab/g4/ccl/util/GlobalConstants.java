package org.eredlab.g4.ccl.util;

/**
 * ������
 * 
 * @author XiongChun
 * @since 2009-07-13
 */
public interface GlobalConstants {
	/**
	 * XML�ĵ����<br>
	 * 0:�ڵ�����ֵ��ʽ
	 */
	public static final String XML_Attribute = "0";

	/**
	 * XML�ĵ����<br>
	 * 1:�ڵ�Ԫ��ֵ��ʽ
	 */
	public static final String XML_Node = "1";

	/**
	 * �ַ����������<br>
	 * number:�����ַ���
	 */
	public static final String S_STYLE_N = "number";

	/**
	 * �ַ����������<br>
	 * letter:��ĸ�ַ���
	 */
	public static final String S_STYLE_L = "letter";

	/**
	 * �ַ����������<br>
	 * numberletter:������ĸ����ַ���
	 */
	public static final String S_STYLE_NL = "numberletter";

	/**
	 * ��ʽ��<br>
	 * FORMAT_DateTime: ����ʱ��
	 */
	public static final String FORMAT_DateTime = "yyyy-MM-dd hh:mm:ss";

	/**
	 * ��ʽ��<br>
	 * FORMAT_DateTime: ����
	 */
	public static final String FORMAT_Date = "yyyy-MM-dd";

	/**
	 * ��ʽ��<br>
	 * FORMAT_DateTime: ʱ��
	 */
	public static final String FORMAT_Time = "hh:mm:ss";

	/**
	 * ���з�<br>
	 * \n:����
	 */
	public static final String ENTER = "\n";

	/**
	 * �쳣��Ϣͳһͷ��Ϣ<br>
	 * �ǳ��ź���֪ͨ��,���������쳣
	 */
	public static final String Exception_Head = "\n�ǳ��ź���֪ͨ��,���������쳣.\n" + "�쳣��Ϣ����:\n";

	/**
	 * Ext������ģʽ<br>
	 * \n:�Ƿ�ҳ�������ģʽ
	 */
	public static final String EXT_GRID_FIRSTLOAD = "first";

	/**
	 * Excelģ����������<br>
	 * number:��������
	 */
	public static final String ExcelTPL_DataType_Number = "number";

	/**
	 * Excelģ����������<br>
	 * number:�ı�����
	 */
	public static final String ExcelTPL_DataType_Label = "label";

	/**
	 * HTTP��������<br>
	 * 1:������
	 */
	public static final String PostType_Nude = "1";

	/**
	 * HTTP��������<br>
	 * 0:��������
	 */
	public static final String PostType_Normal = "0";

	/**
	 * Ajax����ʱ������<br>
	 * 999:��������
	 */
	public static final int Ajax_Timeout = 999;

	/**
	 * ��ҳ��ѯ��ҳ����ȱʧ������Ϣ
	 */
	public static final String ERR_MSG_QUERYFORPAGE_STRING = "������ʹ�÷�ҳ��ѯ,�����㴫�ݵķ�ҳ����ȱʧ!�������Ҫ��ҳ����,�����Գ���ʹ����ͨ��ѯ:queryForList()����";

}