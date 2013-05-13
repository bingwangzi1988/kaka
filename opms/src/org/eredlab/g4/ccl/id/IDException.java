package org.eredlab.g4.ccl.id;

/**
 * IDException
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 * @see RuntimeException
 */
public class IDException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IDException() {
		super("ID�쳣!");
	}

	public IDException(String message, Throwable cause) {
		super(message, cause);
	}

	public IDException(String message) {
		super(message);
	}

	public IDException(Throwable cause) {
		super(cause);
	}

}
