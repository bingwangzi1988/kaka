package org.eredlab.g4.arm.service.impl;

import org.eredlab.g4.arm.service.ProjectHomeService;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;

/**
 * ��Ŀ��ҳ����ӿ�
 * 
 * @author XiongChun
 * @since 2010-12-25
 */
public class ProjectHomeServiceImpl extends BaseServiceImpl implements ProjectHomeService {

	/**
	 * ����������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto saveTopicItem(Dto inDto){
		Dto outDto = new BaseDto();
		g4Dao.insert("saveTopicItem", inDto);
		return outDto;
	}
	
	/**
	 * ��������˴κͻ�������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto updateCount(Dto inDto){
		Dto outDto = new BaseDto();
		g4Dao.update("updateviewcount", inDto);
		return outDto;
	}
	
	/**
	 * ���������Ϣ
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto saveReplyTopic(Dto inDto){
		Dto outDto = new BaseDto();
		g4Dao.update("updateTopicTableWhenReply", inDto);
		g4Dao.insert("saveReplyItem", inDto);
		return outDto;
	}
	
	/**
	 * �޸�������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto editTopic(Dto inDto){
		Dto outDto = new BaseDto();
		g4Dao.update("updateTopic", inDto);
		return outDto;
	}
	
	/**
	 * ɾ��������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto deleteTopic(Dto inDto){
		Dto outDto = new BaseDto();
		g4Dao.delete("deleteTopic", inDto);
		g4Dao.delete("deleteReplyByTopicId", inDto);
		return outDto;
	}
	
	/**
	 * �޸Ļ���
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto editReply(Dto inDto){
		Dto outDto = new BaseDto();
		g4Dao.update("updateReply", inDto);
		return outDto;
	}
	
	/**
	 * ɾ������
	 * 
	 * @param inDto
	 * @return
	 */
	public Dto deleteReply(Dto inDto){
		Dto outDto = new BaseDto();
		g4Dao.delete("deleteReply", inDto);
		return outDto;
	}
}
