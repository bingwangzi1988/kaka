package org.eredlab.g4.ccl.id;

/**
 * InitSequenceGeneratorException
 * ǰ׺������
 * ��Ϊ������ֵ������ΪID��ǰ׺������ȡ��ΪPrefixGenerator.
 * �����ڼ�Ⱥ���������ϵͳ��ͨ����Ҫ��ID����ǰ׺�������Ͳ������������ͻ�����.
 * PrefixGenerator ��ʵ��Ҫ���߳���ȫ�� 
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 */
public interface PrefixGenerator {
  public String create() throws CreatePrefixException;
}
 