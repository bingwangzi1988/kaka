package org.eredlab.g4.arm.service;

import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ��Ŀ��ҳ����ӿ�
 * 
 * @author XiongChun
 * @since 2010-12-25
 */
public interface ProjectHomeService extends BaseService {
	
	/**
	 * ����������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto saveTopicItem(Dto inDto);
	
	/**
	 * ��������˴κͻ�������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto updateCount(Dto inDto);
	
	/**
	 * ���������Ϣ
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto saveReplyTopic(Dto inDto);
	
	/**
	 * �޸�������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto editTopic(Dto inDto);
	
	/**
	 * ɾ��������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto deleteTopic(Dto inDto);
	
	/**
	 * �޸Ļ���
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto editReply(Dto inDto);
	
	/**
	 * ɾ������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto deleteReply(Dto inDto);
	
}
