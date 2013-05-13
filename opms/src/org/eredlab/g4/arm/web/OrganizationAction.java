package org.eredlab.g4.arm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.OrganizationService;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * ��֯����ģ��
 * 
 * @author XiongChun
 * @since 2010-04-10
 * @see BaseAction
 */
public class OrganizationAction extends BaseAction {
	
	private OrganizationService organizationService = (OrganizationService) super.getService("organizationService");
	
	/**
	 * ���Ź���ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward departmentInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.removeSessionAttribute(request, "deptid");
		Dto inDto = new BaseDto();
		String deptid = super.getSessionContainer(request).getUserInfo().getDeptid();
		inDto.put("deptid", deptid);
		Dto outDto = organizationService.queryDeptinfoByDeptid(inDto);
		request.setAttribute("rootDeptid", outDto.getAsString("deptid"));
		request.setAttribute("rootDeptname", outDto.getAsString("deptname"));
		return mapping.findForward("manageDepartmentView");
	}
	
	/**
	 * ���Ź�������ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward departmentTreeInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto dto = new BaseDto();
		String nodeid = request.getParameter("node");
		dto.put("parentid", nodeid);
		Dto outDto = organizationService.queryDeptItems(dto);
		write(outDto.getAsString("jsonString"), response);
		return mapping.findForward(null);
	}
	
	/**
	 * ��ѯ�����б���Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryDeptsForManage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm)form;
		Dto dto = aForm.getParamAsDto(request);
		String deptid = request.getParameter("deptid");
		if (G4Utils.isNotEmpty(deptid)) {
			super.setSessionAttribute(request, "deptid", deptid);
		}
		if(!G4Utils.isEmpty(request.getParameter("firstload"))){
			dto.put("deptid", super.getSessionContainer(request).getUserInfo().getDeptid());
		}else{
			dto.put("deptid", super.getSessionAttribute(request, "deptid"));
		}		
		List menuList = g4Reader.queryForPage("queryDeptsForManage", dto);
		Integer pageCount = (Integer) g4Reader.queryForObject("queryDeptsForManageForPageCount", dto);
		String jsonString = encodeList2PageJson(menuList, pageCount, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * ���沿��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveDeptItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		organizationService.saveDeptItem(inDto);
		Dto outDto = new BaseDto();
		outDto.put("msg", "�������������ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
	
	/**
	 * �޸Ĳ���
	 * 
	 * @param
	 * @return
	 */
	public ActionForward updateDeptItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		organizationService.updateDeptItem(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "���������޸ĳɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
	
	/**
	 * ɾ��������
	 * 
	 * @param
	 * @return
	 */
	public ActionForward deleteDeptItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strChecked = request.getParameter("strChecked");
		String type = request.getParameter("type");
		String deptid = request.getParameter("deptid");
		Dto inDto = new BaseDto();
		inDto.put("strChecked", strChecked);
		inDto.put("type", type);
		inDto.put("deptid", deptid);
		organizationService.deleteDeptItems(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "��������ɾ���ɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
	
	/**
	 * �����û��������ű�Ų�ѯ���Ŷ���<br>
	 * ���ڹ�����֯�������ĸ��ڵ�
	 * @param
	 * @return
	 */
	public ActionForward queryDeptinfoByDeptid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = new BaseDto();
		String deptid = super.getSessionContainer(request).getUserInfo().getDeptid();
		inDto.put("deptid", deptid);
		Dto outDto = organizationService.queryDeptinfoByDeptid(inDto);
		String jsonString = JsonHelper.encodeObject2Json(outDto);
		write(jsonString, response);
		return mapping.findForward(null);
	}
}
