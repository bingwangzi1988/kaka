package org.eredlab.g4.ccl.id;

/**
 * IDGenerator
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 */
public interface IDGenerator {
	public String create() throws CreateIDException;
}
