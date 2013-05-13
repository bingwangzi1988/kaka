package org.eredlab.g4.ccl.id.storer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.id.SequenceStorer;
import org.eredlab.g4.ccl.id.StoreSequenceException;

/**
 * MemorySequenceStorer
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 * @see SequenceStorer
 */
public class MemorySequenceStorer implements SequenceStorer {

	private final Log logger = LogFactory.getLog(MemorySequenceStorer.class);

	private Map cache = new HashMap();

	public void init() {
	}

	public long load(String sequenceID) throws StoreSequenceException {
		if (logger.isDebugEnabled()) {
			logger.debug("��ȡ���ֵ,��ţɣ�:" + sequenceID);
		}
		if (cache.containsKey(sequenceID) == false) {
			updateMaxValueByFieldName(0, sequenceID);
		}
		Long result = (Long) cache.get(sequenceID);
		return result.longValue();
	}

	public void updateMaxValueByFieldName(long sequence, String sequenceID)
			throws StoreSequenceException {
		if (logger.isDebugEnabled()) {
			logger.debug("�������,��ţɣ�:[" + sequenceID + "]���ֵ:" + sequence);
		}
		cache.put(sequenceID, new Long(sequence));
	}

}
