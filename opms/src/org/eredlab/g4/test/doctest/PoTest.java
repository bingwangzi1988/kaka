package org.eredlab.g4.test.doctest;

import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * ������Ƥ���������ʾ��
 * 
 * @author XiongChun
 * @since 2011-03-29
 */
public class PoTest {
	
	public static void main(String[] args) {
		StudentPo studentPo = new StudentPo();
		// ��PO����תΪDto����
		studentPo.toDto();
		// ��PO����ת��ΪJSON���ϸ�ʽ
		studentPo.toJson();
		// ��PO����ת��ΪXML���ϸ�ʽ
		studentPo.toXml(GlobalConstants.XML_Node);
	}

}
