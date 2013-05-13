package org.eredlab.g4.ccl.datastructure.impl;

import java.io.Serializable;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.PKey;
import org.eredlab.g4.ccl.util.G4Utils;


/**
 * ʵ�����<br>
 * ÿһ��ʵ������Ӧһ�����ݿ��ṹ,���ֶηֱ��ʵ���������һһ��Ӧ<br>
 * <b>ע�⣺ʵ�������ֻ������ƽ̨�Ĵ������ɹ�������,�������ֹ��Ķ�
 * @author XiongChun
 * @since 2009-06-23
 * @see java.io.Serializable
 */
public abstract class BasePo implements Serializable{
	
    /**
     * ��ʵ���������ת��ΪDto����
     * @return dto ���ص�Dto����
     */
	public Dto toDto(){
		Dto dto = new BaseDto();
		G4Utils.copyPropFromBean2Dto(this, dto);
	   	//BeanUtils�Զ���getPk()������Ϊ��Bean���Կ�������Dto������,�ʽ����Ƴ�.
    	dto.remove("pk");
		return dto;
	}
	
	/**
	 * ��ʵ���������ת��ΪXML�ַ���
	 * @param pStyle XML�ĵ����
	 * @return String ���ص�XML��ʽ�ַ���
	 */
    public String toXml(String pStyle){
    	Dto dto = toDto();
    	return dto.toXml(pStyle);
    }
    
	/**
	 * ��ʵ���������ת��ΪJSON�ַ���
	 * @return String ���ص�JSON��ʽ�ַ���
	 */
    public String toJson(){
    	Dto dto = toDto();
    	return dto.toJson();
    }
    
    /**
     * ��ȡ������ǿ�Ʒǿռ�ֵ���
     */
    public abstract PKey getPk();

}
