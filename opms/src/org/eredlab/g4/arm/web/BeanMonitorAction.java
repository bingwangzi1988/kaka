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
 * SpringBean���
 * 
 * @author XiongChun
 * @since 2010-09-20
 * @see BaseAction
 */
public class BeanMonitorAction extends BaseAction {

	private MonitorService monitorService = (MonitorService) getService("monitorService");

	/**
	 * SpringBean���ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("beanMonitorView");
	}

	/**
	 * ��ѯ�����Ϣ�б�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryMonitorDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		List eventList = null;
		Integer totalCount = null;
		if (G4Utils.isOracle()) {
			eventList = g4Reader.queryForPage("queryBeanMonitorRecordsByDto", dto);
			totalCount = (Integer) g4Reader.queryForObject("queryBeanMonitorRecordsByDtoForPageCount", dto);
		} else if (G4Utils.isMysql()) {
			eventList = g4Reader.queryForPage("queryBeanMonitorRecordsByDtoMysql", dto);
			totalCount = (Integer) g4Reader.queryForObject("queryBeanMonitorRecordsByDtoForPageCountMysql", dto);
		}
		String jsonString = encodeList2PageJson(eventList, totalCount, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * ɾ�������Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward deleteMonitorDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		monitorService.deleteMonitorData(dto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "����ɾ���ɹ�!");
		if (dto.getAsString("type").equalsIgnoreCase("reset"))
			outDto.put("msg", "���óɹ�,���м�ؼ�¼�ѱ����!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
}
