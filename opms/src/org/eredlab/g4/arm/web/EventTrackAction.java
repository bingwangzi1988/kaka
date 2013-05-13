package org.eredlab.g4.arm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.MonitorService;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * �¼�����Action
 * 
 * @author XiongChun
 * @since 2010-09-12
 * @see BaseAction
 */
public class EventTrackAction extends BaseAction {
	
	private MonitorService monitorService = (MonitorService)super.getService("monitorService");
	
	/**
	 * �¼�����ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("eventTrackView");
	}
	
	/**
	 * ��ѯ�¼��б�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryEvents(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm)form;
		Dto dto = aForm.getParamAsDto(request);
		List eventList = null;
		Integer totalCount = null;
		if (G4Utils.isOracle()) {
			eventList = g4Reader.queryForPage("queryEventsByDto", dto);
			totalCount = (Integer) g4Reader.queryForObject("queryEventsByDtoForPageCount", dto);
		} else {
			eventList = g4Reader.queryForPage("queryEventsByDtoMysql", dto);
			totalCount = (Integer) g4Reader.queryForObject("queryEventsByDtoForPageCountMysql", dto);
		}
		String jsonString = encodeList2PageJson(eventList, totalCount, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * ɾ���¼�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward deleteEvents(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm)form;
		Dto dto = aForm.getParamAsDto(request);
		monitorService.deleteEvent(dto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "����ɾ���ɹ�!");
		if (dto.getAsString("type").equalsIgnoreCase("reset"))
			outDto.put("msg", "���óɹ�,�����¼��ѱ����!");
		super.write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
}
