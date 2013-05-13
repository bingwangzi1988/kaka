package org.eredlab.g4.demo.esb.httpinvoker;

import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BasePo;

/**
 * Httpinvoker�ӿ�
 * 
 * @author XiongChun
 * @since 2010-10-13
 * @see BasePo
 */
public interface HelloWorld {
	/**
	 * ��Ҫ���ⷢ���ķ���
	 * @param text
	 * @return
	 */
	public String sayHello(String text);
	
	/**
	 * ��ѯһ��������ϸ��������
	 * @param jsbh
	 * @return XML�ַ���
	 */
	public Dto queryBalanceInfo(String jsbh);
	
	/**
	 * ��ѯ������ϸ���������б�
	 * @param rownum
	 * @return List
	 */
	public List queryBalanceInfoLimitRownum(Integer rownum);
	
}
