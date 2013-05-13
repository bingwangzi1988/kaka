package org.eredlab.g4.ccl.exception;

import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * �ǿ��쳣У����<br>
 * 
 * @author XiongChun
 * @since 2009-07-15
 */
public class NullAbleException extends RuntimeException {

	private String nullField;

	/**
	 * ���캯��1
	 * 
	 * @param �ǿ�У����
	 */
	public NullAbleException() {
		super(GlobalConstants.Exception_Head + "������Ϊ��,����.");
	}

	/**
	 * ���캯��2
	 * 
	 * @param �ǿ�У����
	 */
	public NullAbleException(Class cs) {
		super(GlobalConstants.Exception_Head + "������Ϊ��,����.[" + cs + "]");
	}

	/**
	 * ���캯��3
	 * 
	 * @param pNullField
	 *            �쳣������Ϣ
	 */
	public NullAbleException(String pNullField) {
		super(GlobalConstants.Exception_Head + "��������[" + pNullField + "]����Ϊ��,����.");
		this.setNullField(pNullField);
	}

	public String getNullField() {
		return nullField;
	}

	public void setNullField(String nullField) {
		this.nullField = nullField;
	}
}
