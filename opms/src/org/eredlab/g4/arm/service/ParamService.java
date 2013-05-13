package org.eredlab.g4.arm.service;

import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/*
 * ȫ�ֲ�������ҵ��ӿ�
 * @author XiongChun
 * @since 2010-05-13
 */
public interface ParamService extends BaseService{

	/**
	 * ���������Ϣ��
	 */
	public Dto saveParamItem(Dto pDto);

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param pDto
	 */
	public Dto deleteParamItem(Dto pDto);

	/**
	 * �޸Ĳ�����Ϣ
	 * 
	 * @param pDto
	 */
	public Dto updateParamItem(Dto pDto);
}
