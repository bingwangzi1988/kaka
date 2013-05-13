package org.eredlab.g4.arm.service;

import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ��Դģ��ҵ��ӿ�
 * 
 * @author XiongChun
 * @since 2010-01-13
 */
public interface ResourceService extends BaseService{
	
	/**
	 * ��������
	 * @param pDto
	 * @return
	 */
	public Dto saveCodeItem(Dto pDto);
	
	/**
	 * ɾ�������
	 * @param pDto
	 * @return
	 */
	public Dto deleteCodeItem(Dto pDto);
	
	/**
	 * �޸Ĵ����
	 * @param pDto
	 * @return
	 */
	public Dto updateCodeItem(Dto pDto);
	
	/**
	 * ����˵�
	 * @param pDto
	 * @return
	 */
	public  Dto saveMenuItem(Dto pDto);
	
	/**
	 * ɾ���˵�
	 * @param pDto
	 * @return
	 */
	public Dto deleteMenuItems(Dto pDto);
	
	/**
	 * �޸Ĳ˵�
	 * @param pDto
	 * @return
	 */
	public Dto updateMenuItem(Dto pDto);
	
}
