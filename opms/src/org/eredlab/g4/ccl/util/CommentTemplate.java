package org.eredlab.g4.ccl.util;

import java.util.HashMap;
import java.util.Map;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;

/**
 * eRedG4��Ʒ����ע�͹淶ģ��
 * ��������ע�͵���������,�ⲿ��֧��HTML���Ŷ��<br>������Ŷ
 * <p>�ı�����ʾŶ<p>�ܷ���Ĺ�<strong>�����Ǵ�����ʾŶ</strong>
 * ��֧�ָ����html���,��鿴��eRedBOS��Ʒ����ע�͹淶���ĵ�
 * @author XiongChun <������>
 * @since 2009-06-22 <������>
 * @see com.eredlab.eredbos.eredccp.datastructure.impl.BaseDto <��ѡ��>
 */
public class CommentTemplate extends BaseDto{
	
	/**
	 * ��ʵ������1��ע��
	 */
	private String instanceVarable1;
	
	/**
	 * ������methodA������˵��<Ҳ֧��HTML��ǵ�Ŷ>
	 * @param pId ���1��˵��
	 * @param pDto ���2��˵��
	 * @return map ����˵��
	 * @exception �쳣˵��
	 */
	public Map methodA(String pId, Dto pDto) throws Exception{
		//�������ڲ��ĵ���ע��
		Map map = new HashMap();
		/*
		 * �������ڲ��Ŀ鼶ע��
		 * ˵�����������ڲ���ע���ܲ�д�Ͳ�д
		 */
		map.put("a", "01");
		String strTest = null;
		if(true){
			//�߼��ж�ע��
			strTest = "A";
		}else{
			//�߼��ж�ע��
			strTest = "B";
		}
		return map;
	}
}
