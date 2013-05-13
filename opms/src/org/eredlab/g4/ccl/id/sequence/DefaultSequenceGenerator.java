package org.eredlab.g4.ccl.id.sequence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.id.CreateSequnceException;
import org.eredlab.g4.ccl.id.InitSequenceGeneratorException;
import org.eredlab.g4.ccl.id.SequenceGenerator;
import org.eredlab.g4.ccl.id.SequenceStorer;
import org.eredlab.g4.ccl.id.storer.FileSequenceStorer;

/**
 * DefaultSequenceGenerator
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 * @see SequenceGenerator
 */
public class DefaultSequenceGenerator implements SequenceGenerator {

	/**
	 * �����Сֵ
	 */
	protected long minValue = 0L;
	/**
	 * ������ֵ
	 */
	protected long maxValue = Long.MAX_VALUE;
	/**
	 * cache��С������ȷ��Ԥ���������;cacheԽ�󣬣ɣ�����Ч��Խ�ߣ����ǵ�ϵͳ �ر�ʱ��������ɵģɣ��˷�Ҳ�����.
	 */
	protected int cache = 100;
	/**
	 * �Ƿ�ѭ�����ɣ���cycle�ﵽ���ֵʱ���Ƿ�ѭ�����ִ���Сֵ��ʼ����
	 */
	protected boolean cycle = true;
	/**
	 * ���ڴ洢����ȡ���ѷ����ȥ��������
	 */
	protected SequenceStorer sequenceStorer = new FileSequenceStorer();

	protected long currCount = 0L;// ��ǰʵ���ѷ���������ֵ
	protected long maxCount = cache + currCount;// ���Է���������ֵ��
	protected String id = "anonymity";// ��ŵı��·���
	protected boolean initiated = false;
	protected final Log logger = LogFactory
			.getLog(DefaultSequenceGenerator.class);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DefaultSequenceGenerator() {

	}

	public DefaultSequenceGenerator(String pId) {
		this.id = pId;
	}

	public void init() {
		initiated = true;
		long initValue = sequenceStorer.load(this.getId());
		initValue = java.lang.Math.max(initValue, minValue);

		if (initValue > maxValue) {
			if (this.cycle) {
				initValue = this.minValue;
			} else {
				final String msg = this.id + " ���������������Ѿ��ﵽ���ֵ:" + maxValue
						+ " �ˣ�ϵͳ�޷��ڷ�����ţ�";
				logger.error(msg);
				throw new InitSequenceGeneratorException(msg);
			}
		}
		currCount = initValue;
		maxCount = currCount + cache;
		maxCount = java.lang.Math.min(maxCount, maxValue);
		sequenceStorer.updateMaxValueByFieldName(maxCount, this.getId());
	}

	public long next() throws CreateSequnceException {
		if (initiated == false) {
			init();
		}
		if (currCount == maxCount) {// ��������id
			long tmp = maxCount + cache;
			if (tmp >= maxValue) {
				if (this.cycle) {
					tmp = this.minValue;
				} else {
					final String msg = this.id + " ���������������Ѿ��ﵽ���ֵ:" + maxValue
							+ " �ˣ�ϵͳ�޷��ڷ�����ţ�";
					logger.error(msg);
					throw new CreateSequnceException(msg);
				}
			}
			sequenceStorer.updateMaxValueByFieldName(tmp, this.getId());
			maxCount = tmp;
		}
		currCount++;
		return currCount;
	}

	public long getMinValue() {
		return minValue;
	}

	public void setMinValue(long minValue) {
		this.minValue = minValue;
	}

	public long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(long maxValue) {
		this.maxValue = maxValue;
	}

	public int getCache() {
		return cache;
	}

	public void setCache(int cache) {
		this.cache = cache;
	}

	public boolean isCycle() {
		return cycle;
	}

	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}

	public void setSequenceStorer(SequenceStorer sequenceStorer) {
		this.sequenceStorer = sequenceStorer;
	}

}
