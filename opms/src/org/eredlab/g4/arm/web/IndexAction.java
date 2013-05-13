package org.eredlab.g4.arm.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.OrganizationService;
import org.eredlab.g4.arm.service.UserService;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

import com.common.util.Utils;

/**
 * ��ҳAction
 * 
 * @author XiongChun
 * @since 2010-01-13
 * @see BaseAction
 */
public class IndexAction extends BaseAction {
	
	private OrganizationService organizationService = (OrganizationService)SpringBeanLoader.getSpringBean("organizationService");

	/**
	 * ��ҳ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward indexInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("sysTitle", getParamValue("SYS_TITLE", request));
		request.setAttribute("westTitle", getParamValue("WEST_NAVIGATE_TITLE", request));
		return mapping.findForward("indexView");
	}

	/**
	 * ��ӭҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward preferencesInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("welcomeView");
	}
	
	/**
	 * �����û��Զ�������
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveUserTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dto dto = new BaseDto();
		String theme = request.getParameter("theme");
		dto.put("userid", super.getSessionContainer(request).getUserInfo().getUserid());
		dto.put("theme", theme);
		Dto outDto = organizationService.saveUserTheme(dto);
		String jsonString = JsonHelper.encodeObject2Json(outDto);
		write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * ���ص�ǰ��¼�û���Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward loadUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfoVo userInfoVo = getSessionContainer(request).getUserInfo();
		Dto inDto = new BaseDto();
		G4Utils.copyPropFromBean2Dto(userInfoVo, inDto);
		Dto outDto = (BaseDto)g4Reader.queryForObject("getUserInfoByKey", inDto);
		outDto.remove("password");
		String jsonString = JsonHelper.encodeDto2FormLoadJson(outDto, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * �޸ĵ�ǰ��¼�û���Ϣ
	 * 
	 * @param
	 * @return
	 */
	public ActionForward updateUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm)form;
		UserService service = (UserService)getService("userService");
		Dto indDto = cForm.getParamAsDto(request);
		indDto.put("chgpwddate", Utils.stringToDate(Utils.dateToString(new Date(),"yyyy-MM-dd"), "yyyy-MM-dd"));
		Dto dto = service.updateUserItem4IndexPage(indDto);
		String jsonString = JsonHelper.encodeObject2Json(dto);
		write(jsonString, response);
		return mapping.findForward(null);
	}

}
