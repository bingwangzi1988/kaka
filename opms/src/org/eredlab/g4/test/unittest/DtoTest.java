package org.eredlab.g4.test.unittest;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * ���ݽṹDTO��Ԫ��������
 * 
 * @author XiongChun
 * @since 2011-03-29
 */
public class DtoTest extends TestCase {
	
	public void main(String args[]){
		TestRunner.run(DtoTest.class);
	}
	
	/**
	 * ����DTO��XML��ת��
	 */
	public void testToXml(){
		Dto dto = new BaseDto();
		dto.put("name", "XiongChun");
		dto.put("age", "28");
		String xml = dto.toXml();
		assertEquals("<root><age>28</age><name>XiongChun</name></root>", xml);
		String xml2 = dto.toXml(GlobalConstants.XML_Attribute);
		assertEquals("<root><row age=\"28\" name=\"XiongChun\"/></root>", xml2);
	}
	
	/**
	 * ����DTO��JSON���ϸ�ʽ��ת��
	 */
	public void testToJson(){
		Dto dto = new BaseDto();
		dto.put("name", "XiongChun");
		dto.put("age", "28");
		String json = dto.toJson();
		assertEquals("{\"age\":\"28\",\"name\":\"XiongChun\"}", json);
	}
}