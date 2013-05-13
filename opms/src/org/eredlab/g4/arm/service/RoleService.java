package org.eredlab.g4.arm.service;

import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ��ɫ��������Ȩҵ��ӿ�
 * @author XiongChun
 * @since 2010-01-13
 */
public interface RoleService extends BaseService{
	
	/**
	 * �����ɫ
	 * @param pDto
	 * @return
	 */
	public Dto saveRoleItem(Dto pDto);
	
	/**
	 * ɾ����ɫ
	 * @param pDto
	 * @return
	 */
	public Dto deleteRoleItems(Dto pDto);
	
	/**
	 * �޸Ľ�ɫ
	 * @param pDto
	 * @return
	 */
	public Dto updateRoleItem(Dto pDto);
	
	/**
	 * �����ɫ��Ȩ��Ϣ
	 * @param pDto
	 * @return
	 */
	public Dto saveGrant(Dto pDto);
	
	/**
	 * �����ɫ�û�������Ϣ
	 * @param pDto
	 * @return
	 */
	public Dto saveSelectUser(Dto pDto);
}
