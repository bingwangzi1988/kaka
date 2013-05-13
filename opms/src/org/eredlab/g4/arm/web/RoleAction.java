package org.eredlab.g4.arm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.OrganizationService;
import org.eredlab.g4.arm.service.RoleService;
import org.eredlab.g4.arm.service.UserService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.util.WebUtils;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * ��ɫ��������Ȩ
 * 
 * @author XiongChun
 * @since 2010-04-21
 * @see BaseAction
 */
public class RoleAction extends BaseAction{
	
	private RoleService roleService = (RoleService) super.getService("roleService");
	private UserService userService = (UserService) super.getService("userService");
	private OrganizationService organizationService = (OrganizationService) super.getService("organizationService");
	
	/**
	 * ��ɫ��������Ȩҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward roleInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.removeSessionAttribute(request, "deptid");
		Dto inDto = new BaseDto();
		String deptid = super.getSessionContainer(request).getUserInfo().getDeptid();
		inDto.put("deptid", deptid);
		Dto outDto = organizationService.queryDeptinfoByDeptid(inDto);
		request.setAttribute("rootDeptid", outDto.getAsString("deptid"));
		request.setAttribute("rootDeptname", outDto.getAsString("deptname"));
		UserInfoVo userInfoVo = getSessionContainer(request).getUserInfo();
		request.setAttribute("login_account", userInfoVo.getAccount());
		return mapping.findForward("manageRoleView");
	}
	
	/**
	 * ��������ʼ��
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
	 * ��ѯ��ɫ�б�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryRolesForManage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		dto.put("roletype", ArmConstants.ROLETYPE_ADMIN);
		UserInfoVo userInfoVo = getSessionContainer(request).getUserInfo();
		if (WebUtils.getParamValue("DEFAULT_ADMIN_ACCOUNT", request).equals(userInfoVo.getAccount())) {
			dto.remove("roletype");
		}
		if (WebUtils.getParamValue("DEFAULT_DEVELOP_ACCOUNT", request).equals(userInfoVo.getAccount())) {
			dto.remove("roletype");
		}
		List roleList = g4Reader.queryForPage("queryRolesForManage", dto);
		Integer pageCount = (Integer)g4Reader.queryForObject("queryRolesForManageForPageCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(roleList, pageCount, null);	
		write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * �����ɫ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveRoleItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		roleService.saveRoleItem(inDto);
		Dto outDto = new BaseDto();
		outDto.put("msg", "��ɫ���������ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
	
	/**
	 * ɾ����ɫ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward deleteRoleItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strChecked = request.getParameter("strChecked");
		Dto inDto = new BaseDto();
		inDto.put("strChecked", strChecked);
		roleService.deleteRoleItems(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "��ɫ����ɾ���ɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
	
	/**
	 * �޸Ľ�ɫ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward updateRoleItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		roleService.updateRoleItem(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "��ɫ�����޸ĳɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
		
	/**
	 * ����Ȩ����Ȩ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward operatorTabInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.removeSessionAttribute(request, "ROLEID_ROLEACTION");
		String roleid = request.getParameter("roleid");
		super.setSessionAttribute(request, "ROLEID_ROLEACTION", roleid);
		return mapping.findForward("operatorTabView");
	}
	
	/**
	 * ѡ����Ա��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward selectUserTabInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("selectUserTabView");
	}
	
	/**
	 * ����Ȩ����Ȩ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward managerTabInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("managerTabView");
	}
	
	/**
	 * �˵�����Ȩ����Ȩ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward managerRoleTabInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("managerRoleTabView");
	}
	
	/**
	 * �����ɫ��Ȩ��Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveGrant(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto inDto = new BaseDto();
		inDto.put("menuid", request.getParameter("menuid"));
		inDto.put("authorizelevel", request.getParameter("key"));
		inDto.put("roleid", super.getSessionAttribute(request, "ROLEID_ROLEACTION"));
		roleService.saveGrant(inDto);
		Dto outDto = new BaseDto();
		if(inDto.getAsString("authorizelevel").equals(ArmConstants.AUTHORIZELEVEL_ACCESS))
		   outDto.put("msg", "����Ȩ����Ȩ�ɹ�");
		if(inDto.getAsString("authorizelevel").equals(ArmConstants.AUTHORIZELEVEL_ADMIN))
			   outDto.put("msg", "����Ȩ����Ȩ�ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
	
	/**
	 * �����ɫ�û�������Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto inDto = new BaseDto();
		inDto.put("userid", request.getParameter("userid"));
		inDto.put("roleid", super.getSessionAttribute(request, "ROLEID_ROLEACTION"));
		roleService.saveSelectUser(inDto);
		Dto outDto = new BaseDto();
		outDto.put("msg", "��ѡ��Ľ�ɫ��Ա�������ݱ���ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
	
	/**
	 * �����ɫ��Ȩ��Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveRoleGrant(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto inDto = new BaseDto();
		inDto.put("menuid", request.getParameter("menuid"));
		inDto.put("authorizelevel", request.getParameter("key"));
		inDto.put("roleid", super.getSessionAttribute(request, "ROLEID_ROLEACTION"));
		userService.saveUserRole(inDto);
		Dto outDto = new BaseDto();
		outDto.put("msg", "��ѡ��Ľ�ɫ�������ݱ���ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
}
