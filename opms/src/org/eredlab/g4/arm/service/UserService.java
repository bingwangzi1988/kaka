package org.eredlab.g4.arm.service;

import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * �û���������Ȩҵ��ӿ�
 * 
 * @author XiongChun
 * @since 2010-01-13
 */
public interface UserService extends BaseService {

	/**
	 * �����û�
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveUserItem(Dto pDto);

	/**
	 * ɾ���û�
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto deleteUserItems(Dto pDto);

	/**
	 * �޸��û�
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto updateUserItem(Dto pDto);

	/**
	 * ������Ա��ɫ������Ϣ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveSelectedRole(Dto pDto);

	/**
	 * ������Ա�˵�������Ϣ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveSelectedMenu(Dto pDto);
	
	/**
	 * ������Ա�˵�������Ϣ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveUserRole(Dto pDto);
	
	/**
	 * �޸��û�(�ṩ��ҳ�޸�ʹ��)
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto updateUserItem4IndexPage(Dto pDto);
	
	/**
	 * �����û�(��ʾϵͳ��ҳע���û�)
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveUserItem4Reg(Dto pDto);
	
	/**
	 * ������Ա��Ȩ�˵�������Ϣ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveEauserroleMenu(Dto pDto);
}
