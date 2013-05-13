package org.eredlab.g4.arm.service;

import java.util.List;

import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ��֯����ģ��ģ��ҵ��ӿ�
 * @author XiongChun
 * @since 2010-01-13
 */
public interface OrganizationService extends BaseService{
	
	/**
	 * ��ȡ�û���Ϣ
	 * @param pDto
	 * @return
	 */
	public Dto getUserInfo(Dto pDto);
	
	/**
	 * ��ѯ������Ϣ���ɲ�����
	 * @param pDto
	 * @return
	 */
	public Dto queryDeptItems(Dto pDto);
	
	/**
	 * ���沿��
	 * @param pDto
	 * @return
	 */
	public Dto saveDeptItem(Dto pDto);
	
	/**
	 * �޸Ĳ���
	 * @param pDto
	 * @return
	 */
	public Dto updateDeptItem(Dto pDto);
	
	/**
	 * ɾ������
	 * @param pDto
	 * @return
	 */
	public Dto deleteDeptItems(Dto pDto);
	
	/**
	 * �����û��������ű�Ų�ѯ���Ŷ���<br>
	 * ���ڹ�����֯�������ĸ��ڵ�
	 * @param
	 * @return
	 */
	public Dto queryDeptinfoByDeptid(Dto pDto);
	
	/**
	 * �����û�������Ϣ
	 * @param pDto
	 */
	public Dto saveUserTheme(Dto pDto);
	
	/**
	 * ��ѯ�û���Ȩ�Ĳ˵�
	 * @param pDto
	 * */
	public List queryUsersMenusByUserId(Dto pDto);
	
	/**
	 * ��ѯ��ɫ��Ȩ�Ĳ˵�
	 * @param pDto
	 * */
	public List queryUsersMenusByRoleId(Dto pDto);
	
	/**
	 * ��ѯ�û��Ľ�ɫ
	 * @param pDto
	 * */
	public List queryUsersRoleByUserId(Dto pDto);
	
	
	/**
	 * ���ݲ˵�ID��ȡ�˵���Ϣ
	 * @param pDto
	 */
	public Dto getMenuInfoByMenuid(Dto pDto);
}
