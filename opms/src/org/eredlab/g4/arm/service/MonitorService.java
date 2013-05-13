package org.eredlab.g4.arm.service;

import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ϵͳ���ҵ��ӿ�
 * @author XiongChun
 * @since 2010-05-13
 */
public interface MonitorService extends BaseService{
	
	/**
	 * ����һ��HTTP�Ự
	 * @param userInfo
	 */
	public void saveHttpSession(UserInfoVo userInfo);
	
	/**
	 * ɾ��һ���йܵĻỰ����
	 * @param pSessionID
	 */
	public void deleteHttpSession(Dto dto);
	
	/**
	 * ����һ���¼�
	 * @param inDto
	 */
	public void saveEvent(Dto dto);
	
	/**
	 * ɾ���¼�
	 * @param inDto
	 */
	public Dto deleteEvent(Dto inDto);

	/**
	 * ɾ��SpringBean��ؼ�¼
	 * 
	 * @param inDto
	 */
	public Dto deleteMonitorData(Dto inDto);
	
	/**
	 * ���ü����Ϣ
	 * 
	 * @param inDto
	 */
	public Dto resetMonitorData();
}
