package org.eredlab.g4.ccl.datastructure.impl;

import java.util.Iterator;

import org.eredlab.g4.ccl.datastructure.PKey;
import org.eredlab.g4.ccl.exception.NullAbleException;

/**
 * �ǿ����ݴ������(DateTransferObject)<br>
 * �������****ByKey�����ݲ����ķ����е����ݴ��ݻ���������Ҫ���в����ǿ���У������ݲ���������ʹ�ô˶������������<br>
 * 
 * @author XiongChun
 * @since 2009-06-23
 */
public class BasePKey extends BaseDto implements PKey {

	/**
	 * �Էǿ����ݴ��������м�ֵ�ǿ���У��
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	public void validateNullAble() {
		if (isEmpty()) {
			try {
				throw new NullAbleException(this.getClass());
			} catch (NullAbleException e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			Iterator keyIterator = keySet().iterator();
			while (keyIterator.hasNext()) {
				String key = (String) keyIterator.next();
				String value = getAsString(key);
				if (value == null || value.equals("")) {
					try {
						throw new NullAbleException(key);
					} catch (NullAbleException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
			}
		}
	}
}
