package org.eredlab.g4.ccl.id;

/**
 * SequenceFormater
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 */
public interface SequenceFormater {
	public String format(long pSequence) throws FormatSequenceExcepiton;
}
