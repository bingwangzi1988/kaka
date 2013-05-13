package org.eredlab.g4.ccl.id.prefix;

import org.eredlab.g4.ccl.id.CreatePrefixException;
import org.eredlab.g4.ccl.id.PrefixGenerator;

/**
 * DefaultPrefixGenerator
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 * @see PrefixGenerator
 */
public class DefaultPrefixGenerator implements PrefixGenerator {

	/**
	 * ǰ׺ֵ
	 */
	private String prefix = "";

	/**
	 * �Ƿ񸽴�����
	 */
	private boolean withDate = false;
	/**
	 * ���ڸ�ʽ
	 */
	private String pattern = "yyyyMMdd";

	public String create() throws CreatePrefixException {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		if (this.withDate) {
			sb.append(getFormatedDate());
		}
		return sb.toString();
	}

	private String getFormatedDate() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				this.pattern);
		java.util.Date now = new java.util.Date();
		return sdf.format(now);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isWithDate() {
		return withDate;
	}

	public void setWithDate(boolean withDate) {
		this.withDate = withDate;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
