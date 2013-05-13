package org.eredlab.g4.arm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.OrganizationService;
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
 * �û���������Ȩ
 * 
 * @author XiongChun
 * @since 2010-04-21
 * @see BaseAction
 */
public class UserAction extends BaseAction {

	private UserService userService = (UserService) super.getService("userService");

	private OrganizationService organizationService = (OrganizationService) super.getService("organizationService");

	/**
	 * �û���������Ȩҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward userInit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.removeSessionAttribute(request, "deptid");
		Dto inDto = new BaseDto();
		String deptid = super.getSessionContainer(request).getUserInfo().getDeptid();
		inDto.put("deptid", deptid);
		Dto outDto = organizationService.queryDeptinfoByDeptid(inDto);
		request.setAttribute("rootDeptid", outDto.getAsString("deptid"));
		request.setAttribute("rootDeptname", outDto.getAsString("deptname"));
		UserInfoVo userInfoVo = getSessionContainer(request).getUserInfo();
		request.setAttribute("login_account", userInfoVo.getAccount());
		return mapping.findForward("manageUserView");
	}

	/**
	 * ���Ź�������ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward departmentTreeInit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dto dto = new BaseDto();
		String nodeid = request.getParameter("node");
		dto.put("parentid", nodeid);
		Dto outDto = organizationService.queryDeptItems(dto);
		write(outDto.getAsString("jsonString"), response);
		return mapping.findForward(null);
	}

	/**
	 * ��ѯ�û��б�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryUsersForManage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		String deptid = request.getParameter("deptid");
		if (G4Utils.isNotEmpty(deptid)) {
			setSessionAttribute(request, "deptid", deptid);
		}
		if (!G4Utils.isEmpty(request.getParameter("firstload"))) {
			dto.put("deptid", super.getSessionContainer(request).getUserInfo().getDeptid());
		} else {
			dto.put("deptid", super.getSessionAttribute(request, "deptid"));
		}
//		dto.put("usertype", ArmConstants.USERTYPE_ADMIN);
		UserInfoVo userInfoVo = getSessionContainer(request).getUserInfo();
		if (WebUtils.getParamValue("DEFAULT_ADMIN_ACCOUNT", request).equals(userInfoVo.getAccount())) {
			dto.remove("usertype");
		}
		if (WebUtils.getParamValue("DEFAULT_DEVELOP_ACCOUNT", request).equals(userInfoVo.getAccount())) {
			dto.remove("usertype");
		}
		List userList = g4Reader.queryForPage("queryUsersForManage", dto);
		Integer pageCount = (Integer) g4Reader.queryForObject("queryUsersForManageForPageCount", dto);
		String jsonString = JsonHelper.encodeList2PageJson(userList, pageCount, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * �����û�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveUserItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		Dto outDto = userService.saveUserItem(inDto);
		String jsonString = JsonHelper.encodeObject2Json(outDto);
		write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * ɾ���û�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward deleteUserItems(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strChecked = request.getParameter("strChecked");
		Dto inDto = new BaseDto();
		inDto.put("strChecked", strChecked);
		userService.deleteUserItems(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�û�����ɾ���ɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}

	/**
	 * �޸��û�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward updateUserItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pwdFlag = request.getParameter("pwdFlag");
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		if (null != pwdFlag && !"".equals(pwdFlag.trim()) && "1".equals(pwdFlag.trim())) {
			inDto.put("chgpwddate", "");
		}
		userService.updateUserItem(inDto);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�û������޸ĳɹ�!");
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}

	/**
	 * �û���Ȩҳ���ʼ��:ѡ���ɫ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward userGrantInit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.removeSessionAttribute(request, "USERID_USERACTION");
		String userid = request.getParameter("userid");
		super.setSessionAttribute(request, "USERID_USERACTION", userid);
		return mapping.findForward("selectRoleTreeView");
	}

	/**
	 * �û���Ȩҳ���ʼ��:ѡ��˵�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward selectMenuInit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("selectMenuTreeView");
	}

	/**
	 * �û���Ȩҳ���ʼ��:ѡ��˵�
	 * 
	 * @param
	 * @return
	 */
	public ActionForward selectUserRoleInit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("selectUserRoleView");
	}

	/**
	 * �����û���ɫ������Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveSelectedRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dto inDto = new BaseDto();
		inDto.put("roleid", request.getParameter("roleid"));
		inDto.put("userid", super.getSessionAttribute(request, "USERID_USERACTION"));
		userService.saveSelectedRole(inDto);
		Dto outDto = new BaseDto();
		outDto.put("msg", "��ѡ�����Ա��ɫ�������ݱ���ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}

	/**
	 * �����û��˵�������Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveSelectedMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dto inDto = new BaseDto();
		inDto.put("menuid", request.getParameter("menuid"));
		inDto.put("userid", super.getSessionAttribute(request, "USERID_USERACTION"));
		userService.saveSelectedMenu(inDto);
		Dto outDto = new BaseDto();
		outDto.put("msg", "��ѡ�����Ա�˵��������ݱ���ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}

	/**
	 * �����û���Ȩ�˵�������Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveSelectedUserRoleMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dto inDto = new BaseDto();
		inDto.put("menuid", request.getParameter("menuid"));
		inDto.put("userid", super.getSessionAttribute(request, "USERID_USERACTION"));
		userService.saveEauserroleMenu(inDto);
		Dto outDto = new BaseDto();
		outDto.put("msg", "��ѡ�����Ա�˵��������ݱ���ɹ�");
		outDto.put("success", new Boolean(true));
		write(outDto.toJson(), response);
		return mapping.findForward(null);
	}
}
