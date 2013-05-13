package org.eredlab.g4.arm.service.impl;

import org.eredlab.g4.arm.service.MonitorService;
import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;

/**
 * ϵͳ���ҵ��ӿ�
 * 
 * @author XiongChun
 * @since 2010-09-13
 */
public class MonitorServiceImpl extends BaseServiceImpl implements MonitorService {

	/**
	 * ����һ��HTTP�Ự
	 * 
	 * @param userInfo
	 */
	public void saveHttpSession(UserInfoVo userInfo) {
		g4Dao.insert("saveHttpSession", userInfo);
	}

	/**
	 * ɾ��һ���йܵĻỰ����
	 * 
	 * @param pSessionID
	 */
	public void deleteHttpSession(Dto dto) {
		g4Dao.delete("deleteHttpSession", dto);
	}

	/**
	 * ����һ���¼�
	 * 
	 * @param inDto
	 */
	public void saveEvent(Dto dto) {
		String eventid = IDHelper.getEventID();
		dto.put("eventid", eventid);
		g4Dao.insert("saveEvent", dto);
	}

	/**
	 * ɾ���¼�
	 * 
	 * @param inDto
	 */
	public Dto deleteEvent(Dto inDto) {
		if (inDto.getAsString("type").equalsIgnoreCase("reset")) {
			g4Dao.delete("resetEvent");
		} else {
			String[] checked = inDto.getAsString("strChecked").split(",");
			for (int i = 0; i < checked.length; i++) {
				g4Dao.delete("deleteEvent", checked[i]);
			}
		}
		return null;
	}

	/**
	 * ɾ��SpringBean��ؼ�¼
	 * 
	 * @param inDto
	 */
	public Dto deleteMonitorData(Dto inDto) {
		if (inDto.getAsString("type").equalsIgnoreCase("reset")) {
			g4Dao.delete("resetBeanMonitorRecords");
		} else {
			String[] checked = inDto.getAsString("strChecked").split(",");
			for (int i = 0; i < checked.length; i++) {
				g4Dao.delete("deleteBeanMonitorRecord", checked[i]);
			}
		}
		return null;
	}

	/**
	 * ���ü����Ϣ
	 * 
	 * @param inDto
	 */
	public Dto resetMonitorData() {
		g4Dao.delete("resetJdbcMonitorData");
		return null;
	}

}
