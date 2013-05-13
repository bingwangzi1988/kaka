package org.eredlab.g4.arm.service;

import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/*
 * Ȩ��ģ�ͱ�ǩҵ��ӿ�
 * @author XiongChun
 * @since 2010-05-13
 */
public interface ArmTagSupportService extends BaseService{
	/**
	 * ��ȡ��Ƭ
	 * @param pDto
	 * @return
	 */
	public Dto getCardList(Dto pDto);
	
	/**
	 * ��ȡ��Ƭ����
	 * @param pDto
	 * @return
	 */
	public Dto getCardTreeList(Dto pDto);
	
	/**
	 * ��ȡ��¼��Ա����������Ϣ
	 * @return
	 */
	public Dto getDepartmentInfo(Dto pDto);
	
	/**
	 * ��ȡ��¼��Ա������Ϣ
	 * @param pDto
	 * @return
	 */
	public Dto getEauserSubInfo(Dto pDto);
}
