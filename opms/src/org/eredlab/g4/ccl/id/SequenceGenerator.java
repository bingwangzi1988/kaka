package org.eredlab.g4.ccl.id;

/**
 * SequenceGenerator �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 */
public interface SequenceGenerator {
	public long next() throws CreateSequnceException;
}
