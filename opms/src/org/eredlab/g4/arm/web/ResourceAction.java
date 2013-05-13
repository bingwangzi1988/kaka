package org.eredlab.g4.arm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.ResourceService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * ��Դģ��
 * 
 * @author XiongChun
 * @since 2010-01-31
 * @see BaseAction
 */
public class ResourceAction extends BaseAction {

	private ResourceService resourceService = (ResourceService) super.getService("resourceService");

	/**
	 * �˵���Դ����ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward menuResourceInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.removeSessionAttribute(request, "menuid");
		Dto dto = (Dto)g4Reader.queryForObject("queryEamenuByMenuID", "01");
		request.setAttribute("rootMenuName", dto.getAsString("menuname"));
		return mapping.findForward("manageMenuResourceView");
	}

	/**
	 * ��ѯ�˵���Ŀ ���ɲ˵���
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryMenuItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto dto = new BaseDto();
		String nodeid = request.getParameter("node");
		dto.put("parentid", nodeid);
		List menuList = g4Reader.queryForList("queryMenuItemsByDto", dto);
		Dto menuDto = new BaseDto();
		for (int i = 0; i < menuList.size(); i++) {
			menuDto = (BaseDto) menuList.get(i);
			if (menuDto.getAsString("leaf").equals(ArmConstants.LEAF_Y))
				menuDto.put("leaf", new Boolean(true));
			else
				menuDto.put("leaf", new Boolean(false));
			if (menuDto.getAsString("id").length() == 4)
				// ID����Ϊ4�Ľڵ��Զ�չ��
				menuDto.put("expanded", new Boolean(true));
		}
		write(JsonHelper.encodeObject2Json(menuList), response);
		return mapping.findForward(null);
	}

	/**
	 * ��ѯ�˵���Ŀ - �˵�����
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryMenuItemsForManage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		String menuid = request.getParameter("menuid");
		if (G4Utils.isNotEmpty(menuid)) {
			super.setSessionAttribute(request, "menuid", menuid);
		}
		dto.put("menuid", super.getSessionAttribute(request, "menuid"));
		List menuList = g4Reader.queryForPage("queryMenuItemsForManage", dto);
		Integer pageCount = (Integer) g4Reader.queryForObject("queryMenuItemsForManageForPageCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(menuList, pageCount, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * ����˵�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveMenuItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		resourceService.saveMenuItem(inDto);
		Dto outDto = new BaseDto();
		outDto.put("msg", "�˵����������ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}

	/**
	 * �޸Ĳ˵�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward updateMenuItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		resourceService.updateMenuItem(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�˵������޸ĳɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}

	/**
	 * ɾ���˵���
	 * 
	 * @param
	 * @return
	 */
	public ActionForward deleteMenuItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strChecked = request.getParameter("strChecked");
		String type = request.getParameter("type");
		String menuid = request.getParameter("menuid");
		Dto inDto = new BaseDto();
		inDto.put("strChecked", strChecked);
		inDto.put("type", type);
		inDto.put("menuid", menuid);
		resourceService.deleteMenuItems(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�˵�����ɾ���ɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}

	/**
	 * ��������ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward codeTableInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("codeTableView");
	}

	/**
	 * ��ѯ�����
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryCodeItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		List codeList = g4Reader.queryForPage("getCodeListForPage", inDto);
		Integer totalCount = (Integer) g4Reader.queryForObject("getCodeListForPageCount", inDto);
		String jsonStrList = JsonHelper.encodeList2PageJson(codeList, totalCount, null);
		write(jsonStrList, response);
		return mapping.findForward(null);
	}

	/**
	 * ��������
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveCodeItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		Dto outDto = resourceService.saveCodeItem(inDto);
		String jsonString = JsonHelper.encodeObject2Json(outDto);
		write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * ɾ�������
	 * 
	 * @param
	 * @return
	 */
	public ActionForward deleteCodeItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strChecked = request.getParameter("strChecked");
		Dto inDto = new BaseDto();
		inDto.put("strChecked", strChecked);
		resourceService.deleteCodeItem(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�ֵ�����ɾ���ɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}

	/**
	 * �޸Ĵ����
	 * 
	 * @param
	 * @return
	 */
	public ActionForward updateCodeItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		Dto outDto=resourceService.updateCodeItem(inDto);
//		Dto outDto = new BaseDto();
//		outDto.put("success", new Boolean(true));
//		outDto.put("msg", "�ֵ������޸ĳɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
	
	/**
	 * �ֵ��ڴ�ͬ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward synMemory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
	    List codeList = g4Reader.queryForList("getCodeViewList");
	    getServlet().getServletContext().removeAttribute("EACODELIST");
	    getServlet().getServletContext().setAttribute("EACODELIST", codeList);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * ϵͳͼ��ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward iconInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("manageIconView");
	}

	/**
	 * ��ѯϵͳͼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryIconItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		List iconList = null;
		if (G4Utils.isOracle()) {
			iconList = g4Reader.queryForPage("queryIconsForManage", inDto);
		} else if (G4Utils.isMysql()) {
			iconList = g4Reader.queryForPage("queryIconsForManageMysql", inDto);
		}
		Integer pageCount = (Integer) g4Reader.queryForObject("queryIconsForManageForPageCount", inDto);
		String jsonString = JsonHelper.encodeList2PageJson(iconList, pageCount, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * ��ɫ��ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward colorPaletteInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("colorPaletteView");
	}
}
