package org.eredlab.g4.ccl.datastructure.impl;

import java.io.Serializable;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * ��ֵ����<br>
 * ��ֵ��������ݿ��û��һһ��Ӧ��ϵ<br>
 * <b>ע�⣺û��������Ҫ���鲻��VO,���ڹ̶������ݴ洢�ṹ��ʹ��PO;���ڶ�̬�ɱ��
 * �����ݴ洢�ṹ��ʹ��Dto������.
 * @author XiongChun
 * @since 2009-06-23
 * @see java.io.Serializable
 */
public abstract class BaseVo implements Serializable{
	
    /**
     * ��ֵ����ת��ΪDto����
     * 
     * @return dto ���ص�Dto����
     */
	public Dto toDto(){
		Dto dto = new BaseDto();
		G4Utils.copyPropFromBean2Dto(this, dto);
		return dto;
	}
	
	/**
	 * ��ֵ����ת��ΪXML�ַ���
	 * @param pStyle XML�ĵ����
	 * @return String ���ص�XML��ʽ�ַ���
	 */
    public String toXml(String pStyle){
    	Dto dto = toDto();
    	return dto.toXml(pStyle);
    }
    
	/**
	 * ��ֵ����ת��ΪJSON�ַ���
	 * @return String ���ص�JSON��ʽ�ַ���
	 */
    public String toJson(){
    	Dto dto = toDto();
    	return dto.toJson();
    }
}
