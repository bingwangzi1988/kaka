package org.eredlab.g4.ccl.id.generator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.id.CreateIDException;
import org.eredlab.g4.ccl.id.IDGenerator;
import org.eredlab.g4.ccl.id.PrefixGenerator;
import org.eredlab.g4.ccl.id.SequenceFormater;
import org.eredlab.g4.ccl.id.SequenceGenerator;
import org.eredlab.g4.ccl.id.sequence.DefaultSequenceGenerator;

/**
 * DefaultIDGenerator �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 * @see IDGenerator
 */
public class DefaultIDGenerator implements IDGenerator {

	private PrefixGenerator prefixGenerator;
	private SequenceGenerator sequenceGenerator = new DefaultSequenceGenerator();
	private SequenceFormater sequenceFormater;

	private final Log logger = LogFactory.getLog(DefaultIDGenerator.class);

	public synchronized String create() throws CreateIDException {
		final String prefix = prefixGenerator == null ? "" : prefixGenerator.create();
		logger.debug("IDǰ׺��:[" + prefix + "]");
		long sequence = sequenceGenerator.next();
		final String strSequence = sequenceFormater == null ? new Long(sequence).toString() : sequenceFormater
				.format(sequence);
		return prefix + strSequence;
	}

	public void setPrefixGenerator(PrefixGenerator prefixGenerator) {
		this.prefixGenerator = prefixGenerator;
	}

	public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	public void setSequenceFormater(SequenceFormater sequenceFormater) {
		this.sequenceFormater = sequenceFormater;
	}

}
