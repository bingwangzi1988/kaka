package org.eredlab.g4.demo.esb.webservice;

import javax.jws.WebService;

import org.eredlab.g4.ccl.datastructure.impl.BasePo;

/**
 * WebService�ӿ�
 * 
 * @author XiongChun
 * @since 2010-10-13
 * @see BasePo
 */
@WebService
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
	public String queryBalanceInfo(String jsbh);
	
	/**
	 * ��ѯ������ϸ���������б�
	 * @param rownum
	 * @return XML�ַ���
	 */
	public String queryBalanceInfoLimitRownum(Integer rownum);
	
	
}
