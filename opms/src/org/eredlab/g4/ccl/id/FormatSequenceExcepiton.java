package org.eredlab.g4.ccl.id;

/**
 * FormatSequenceExcepiton
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 * @see IDException
 */
public class FormatSequenceExcepiton extends IDException {

	public FormatSequenceExcepiton() {
		super("��ʽ������쳣!");
	}

	public FormatSequenceExcepiton(String message, Throwable cause) {
		super(message, cause);
	}

	public FormatSequenceExcepiton(String message) {
		super(message);
	}

	public FormatSequenceExcepiton(Throwable cause) {
		super(cause);
	}

}
