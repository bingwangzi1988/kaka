package org.eredlab.g4.demo.esb.webservice;

import java.util.List;

import javax.jws.WebService;

import org.eredlab.g4.bmf.base.IReader;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BasePo;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.xml.XmlHelper;

/**
 * WebServiceʵ����
 * 
 * @author XiongChun
 * @since 2010-10-13
 * @see BasePo
 */
@WebService
public class HelloWorldImpl implements HelloWorld {
	/**
	 * sayHello
	 */
	public String sayHello(String text) {
		return "Hello," + text;
	}
	
	/**
	 * ��ѯһ��������ϸ��������
	 * @param jsbh
	 * @return XML�ַ���
	 */
	public String queryBalanceInfo(String jsbh){
		IReader reader = (IReader)SpringBeanLoader.getSpringBean("g4Reader");
		Dto inDto = new BaseDto("sxh", jsbh);
		Dto outDto = (BaseDto)reader.queryForObject("queryBalanceInfo", inDto);
		String outXml = XmlHelper.parseDto2Xml(outDto, "root", "balanceInfo");
		return outXml;
	}
	
	/**
	 * ��ѯ������ϸ���������б�
	 * @param rownum
	 * @return XML�ַ���
	 */
	public String queryBalanceInfoLimitRownum(Integer rownum){
		IReader reader = (IReader)SpringBeanLoader.getSpringBean("g4Reader");
		Dto inDto = new BaseDto("rownum", rownum);
		List outList = reader.queryForList("queryBalanceInfo", inDto);
		String outXml = XmlHelper.parseList2Xml(outList, "root", "row");
		return outXml;
	}

}
