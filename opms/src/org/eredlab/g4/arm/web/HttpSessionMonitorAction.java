package org.eredlab.g4.arm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.MonitorService;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.util.SessionContainer;
import org.eredlab.g4.rif.util.SessionListener;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * HTTP�Ự���
 * @author XiongChun
 * @since 2010-09-03
 * @see BaseAction
 */
public class HttpSessionMonitorAction extends BaseAction {

	private MonitorService monitorService = (MonitorService)getService("monitorService");

	/**
	 * �Ự���ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("sessionMonitorView");
	}
	
	/**
	 * ��ȡ��ǰ����û��б�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward getSessionList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm)form;
		Dto dto = cForm.getParamAsDto(request);
		List sessionList = g4Reader.queryForPage("queryHttpSessions", dto);
		Integer pageCount = (Integer) g4Reader.queryForObject("queryHttpSessionsForPageCount", dto);
		String jsonString = encodeList2PageJson(sessionList, pageCount, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * ɱ���Ự
	 * 
	 * @param
	 * @return
	 */
	public ActionForward killSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strChecked = request.getParameter("strChecked");
		String[] sessionid = strChecked.split(",");
		Dto delDto = new BaseDto();
		String msg = "ѡ�еĻỰ��ɱ��!";
		for (int i = 0; i < sessionid.length; i++) {
			String seid = sessionid[i];
			delDto.put("sessionid", seid);
			if(!seid.equalsIgnoreCase(request.getSession().getId())){
				monitorService.deleteHttpSession(delDto);
				HttpSession session = SessionListener.getSessionByID(seid);
				if(G4Utils.isNotEmpty(seid)){
					SessionContainer sessionContainer =  (SessionContainer)session.getAttribute("SessionContainer");
					sessionContainer.setUserInfo(null); //���RequestFilter��������
					sessionContainer.cleanUp();
				}
			}else {
				msg += " ��ʾ��������ɱŶ!";
			}
		}
		Dto dto = new BaseDto();
		dto.put("success", new Boolean(true));
		dto.put("msg", msg);
	    write(JsonHelper.encodeObject2Json(dto), response);
		return mapping.findForward(null);
	}
}