package org.eredlab.g4.ccl.id;

/**
 * SequenceStorer
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 */
public interface SequenceStorer {

	/**
	 * �������
	 * 
	 * @param pSequence
	 *            ���
	 * @param pSequenceID
	 *            ���ID
	 * @throws StoreSequenceException
	 */
	public void updateMaxValueByFieldName(long pSequence, final String pSequenceID)
			throws StoreSequenceException;

	/**
	 * 
	 * @param pSequenceID
	 *            ���ID
	 * @return
	 * @throws StoreSequenceException
	 */
	public long load(final String pSequenceID) throws StoreSequenceException;
}
