package org.eredlab.g4.arm.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.arm.service.MonitorService;
import org.eredlab.g4.arm.service.OrganizationService;
import org.eredlab.g4.arm.service.UserService;
import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.rif.taglib.util.TagConstant;
import org.eredlab.g4.rif.util.SessionContainer;
import org.eredlab.g4.rif.util.SessionListener;
import org.eredlab.g4.rif.util.WebUtils;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

import com.common.business.commonbus.service.CommonService;
import com.common.util.Utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ��¼ҳ��Action
 * 
 * @author XiongChun
 * @see BaseAction
 * @since 2010-01-13
 */
public class LoginAction extends BaseAction {

	private static Log log = LogFactory.getLog(LoginAction.class);

	private OrganizationService organizationService = (OrganizationService) super.getService("organizationService");

	private MonitorService monitorService = (MonitorService) super.getService("monitorService");

	/**
	 * ��½ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bannerPath = getParamValue("LOGIN_WINDOW_BANNER", request);
		String loginWindowTitle = getParamValue("LOGIN_WINDOW_TITLE", request);
		String pageLoadMsg = getParamValue("PAGE_LOAD_MSG", request);

		bannerPath = request.getContextPath() + bannerPath;
		PropertiesHelper p = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);

		ServletContext context = request.getSession().getServletContext();
		if (context != null) {
			List paramList = (List) context.getAttribute("EAPARAMLIST");
			if (paramList != null && paramList.size() > 0) {
				for (int i = 0; i < paramList.size(); i++) {
					Dto paramDto = (BaseDto) paramList.get(i);
					request.setAttribute(paramDto.getAsString("paramkey"), paramDto.getAsString("paramvalue"));
				}
			}
		}
		request.setAttribute("ajaxErrCode", GlobalConstants.Ajax_Timeout);
		request.setAttribute("runMode", p.getValue("runMode", TagConstant.RUN_MODE_NORMAL));
		request.setAttribute("bannerPath", bannerPath);
		request.setAttribute("LOGIN_WINDOW_TITLE", loginWindowTitle);
		request.setAttribute("PAGE_LOAD_MSG", pageLoadMsg);
		request.setAttribute("sysTitle", getParamValue("SYS_TITLE", request));
		return mapping.findForward("loginView");
	}

	/**
	 * ��½�����֤
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		synchronized (response) {

			String account = request.getParameter("account");
			String password = request.getParameter("password");
			// System.out.println(password);
			// password = G4Utils.encryptBasedDes(password);
			log.info("�ʻ�[" + account + "]�����Ե�½ϵͳ...");
			Dto dto = new BaseDto();
			dto.put("account", account);
			Dto outDto = organizationService.getUserInfo(dto);
			UserInfoVo userInfo = (UserInfoVo) outDto.get("userInfo");
			Dto jsonDto = new BaseDto();
			if (G4Utils.isEmpty(userInfo)) {
				jsonDto.put("success", new Boolean(false));
				jsonDto.put("msg", "�ʺŻ������������,����������!");
				jsonDto.put("errorType", "1");
				log.warn("�ʻ�[" + account + "]��½ʧ��.(ʧ��ԭ�򣺲����ڴ��ʻ�)");
				write(jsonDto.toJson(), response);
				return mapping.findForward("");
			}
			if ("1".equals(userInfo.getLocked())) {
				jsonDto.put("success", new Boolean(false));
				jsonDto.put("msg", "�û�������������ϵ����Ա!");
				jsonDto.put("errorType", "1");
				log.warn("�ʻ�[" + account + "]��½ʧ��.(ʧ��ԭ���ʻ�������)");
				write(jsonDto.toJson(), response);
				return mapping.findForward("");
			}
			// System.out.println(password);
			// System.out.println(userInfo.getPassword());

			if (!password.equals(userInfo.getPassword())) {
				jsonDto.put("success", new Boolean(false));
				jsonDto.put("msg", "�ʺŻ������������,����������!");
				jsonDto.put("errorType", "2");
				log.warn(userInfo.getUsername() + "[" + userInfo.getAccount() + "]" + "��¼ϵͳʧ��(ʧ��ԭ�������������)");
				write(jsonDto.toJson(), response);
				return mapping.findForward("");
			}
			String multiSession = WebUtils.getParamValue("MULTI_SESSION", request);
			if ("0".equals(multiSession)) {
				Integer sessions = (Integer) g4Reader.queryForObject("countHttpSessions", account);
				if (sessions.intValue() > 0) {
					jsonDto.put("success", new Boolean(false));
					jsonDto.put("msg", "���û��Ѿ���¼,ϵͳ������������Ự����!");
					jsonDto.put("errorType", "3");
					log.warn(userInfo.getUsername() + "[" + userInfo.getAccount() + "]" + "��¼ϵͳʧ��(ʧ��ԭ�򣺴��û��Ѿ���¼,ϵͳ������������Ự����)");
					write(jsonDto.toJson(), response);
					return mapping.findForward("");
				}
			}

			// һ���û�ֻ����һ̨�����ϵ�¼ start
			CommonActionForm cForm = (CommonActionForm) form;
			Dto sessiondto = cForm.getParamAsDto(request);
			sessiondto.put("start", 0);
			sessiondto.put("end", 1000);
			List sessionList = g4Reader.queryForPage("queryHttpSessions", sessiondto);
			if (sessionList != null && sessionList.size() > 0) {
				for (int i = 0; i < sessionList.size(); i++) {
					UserInfoVo uinfo = (UserInfoVo) sessionList.get(i);
					if (uinfo.getAccount().equals(account)) {
						Dto delDto = new BaseDto();
						String seid = uinfo.getSessionID();
						delDto.put("sessionid", seid);
						if (!seid.equalsIgnoreCase(request.getSession().getId())) {
							monitorService.deleteHttpSession(delDto);
							HttpSession session = SessionListener.getSessionByID(seid);
							if (G4Utils.isNotEmpty(seid) && session != null) {
								SessionContainer sessionContainer = (SessionContainer) session.getAttribute("SessionContainer");
								sessionContainer.setUserInfo(null); // ���RequestFilter��������
								sessionContainer.cleanUp();
							}
						}
					}
				}
			}
			// end
			CommonService commonService = (CommonService) SpringBeanLoader.getSpringBean("commonService");

			// ǿ���޸�����
			// start
			Date chgPwdDate = userInfo.getChgPwdDate();
			Date nowDate = new Date();
			String chgPwdFlag = "0"; // �Ƿ���Ҫǿ���޸����� 0 ��1��
			String pwdDay = "30";
			// ��ȡ�����������
			List pwddaylst = commonService.getForeignLinks("CMPARM", "PARM_ID", "PARM_ID", "PARM_VALUE", "PwdDay", null, null);
			if (pwddaylst != null && pwddaylst.size() > 0) {
				Object[] objects = (Object[]) pwddaylst.get(0);
				if (objects != null && objects.length > 1) {
					pwdDay = objects[1] + "";
				}
			}
			int days = 30;
			try {
				days = Integer.parseInt(pwdDay);
			} catch (Exception e) {
				days = 30;
			}

			// �ж��û��Ƿ��״ε�½
			if (chgPwdDate == null) {
				chgPwdFlag = "1";
			} else {
				Long day = getDateMinusResult(nowDate, chgPwdDate);// ��ǰ�����������޸�����֮���������
				if ((days - day) <= 5) {
					chgPwdFlag = "3";
					jsonDto.put("days", (days - day));
				}

				// �����޸�����+��������
				Date newChePwdDate = Utils.getAddDate(chgPwdDate, days);
				// ��������޸�����+�����������ڵ���������ǿ���޸�����
				if (newChePwdDate.before(nowDate)) {
					chgPwdFlag = "2";
				}

			}

			jsonDto.put("chgPwdFlag", chgPwdFlag);
			// end

			jsonDto.put("success", new Boolean(true));
			userInfo.setSessionID(request.getSession().getId());
			userInfo.setSessionCreatedTime(G4Utils.getCurrentTime());
			userInfo.setLoginIP(request.getRemoteAddr());
			userInfo.setExplorer(G4Utils.getClientExplorerType(request));
			Dto qDto = new BaseDto();
			qDto.put("deptid", userInfo.getDeptid());
			userInfo.setDeptname(organizationService.queryDeptinfoByDeptid(qDto).getAsString("deptname"));
			super.getSessionContainer(request).setUserInfo(userInfo);
			log.info(userInfo.getUsername() + "[" + userInfo.getAccount() + "]" + "�ɹ���¼ϵͳ!������һ����ЧSession����,�ỰID:[" + request.getSession().getId() + "]" + G4Utils.getCurrentTime());
			SessionListener.addSession(request.getSession(), userInfo); // ������ЧSession
			if (pHelper.getValue("requestMonitor", "0").equals("1")) {
				saveLoginEvent(userInfo, request);
			}

			List list = commonService.getForeignLinks("CMSYSINFO", null, "ID", "TRAN_DATE", null, null, null);
			if (list != null && list.size() > 0) {
				for (Object o : list) {
					Object[] objects = (Object[]) o;
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date tranDate = sdf.parse((String) objects[1]);
						request.getSession().setAttribute("TranDate", sdf.format(tranDate));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			write(jsonDto.toJson(), response);
			return mapping.findForward("");
		}
	}

	/**
	 * �˳���¼
	 * 
	 * @param
	 * @return
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfoVo userInfo = super.getSessionContainer(request).getUserInfo();
		if (G4Utils.isNotEmpty(userInfo)) {
			if (pHelper.getValue("requestMonitor", "0").equals("1")) {
				saveLogoutEvent(userInfo, request);
			}
			log.info(userInfo.getUsername() + "�˳���ϵͳ!");
			super.getSessionContainer(request).setUserInfo(null);
		}
		if (G4Utils.isNotEmpty(request.getSession())) {
			request.getSession().invalidate();
		}
		init(mapping, form, request, response);
		return mapping.findForward("loginView");
	}

	/**
	 * �����¼�¼�
	 * 
	 * @param userInfo
	 */
	private void saveLoginEvent(UserInfoVo userInfo, HttpServletRequest request) {
		Dto dto = new BaseDto();
		dto.put("account", userInfo.getAccount());
		dto.put("activetime", G4Utils.getCurrentTime());
		dto.put("userid", userInfo.getUserid());
		dto.put("username", userInfo.getUsername());
		dto.put("description", "��¼ϵͳ");
		dto.put("requestpath", request.getRequestURI());
		dto.put("methodname", request.getParameter("reqCode"));
		dto.put("eventid", IDHelper.getEventID());
		monitorService.saveEvent(dto);
	}

	/**
	 * �����˳��¼�
	 * 
	 * @param userInfo
	 */
	private void saveLogoutEvent(UserInfoVo userInfo, HttpServletRequest request) {
		Dto dto = new BaseDto();
		dto.put("account", userInfo.getAccount());
		dto.put("activetime", G4Utils.getCurrentTime());
		dto.put("userid", userInfo.getUserid());
		dto.put("username", userInfo.getUsername());
		dto.put("description", "�˳�ϵͳ");
		dto.put("requestpath", request.getRequestURI());
		dto.put("methodname", request.getParameter("reqCode"));
		dto.put("eventid", IDHelper.getEventID());
		monitorService.saveEvent(dto);
	}

	/**
	 * ע�����ʻ�(������ʾϵͳ��Ŀ��ҳʹ��)
	 * 
	 * @param
	 * @return
	 */
	public ActionForward regAccount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		UserService userService = (UserService) super.getService("userService");
		Dto dto = aForm.getParamAsDto(request);
		dto.put("sex", "0");
		dto.put("deptid", "001003");
		dto.put("roleid", "10000056");
		dto.put("locked", "0");
		// ��Ŀ��ҳע���û�
		dto.put("usertype", "1");
		dto.put("remark", "��Ŀ��ҳע���û�");
		Dto outDto = userService.saveUserItem4Reg(dto);
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * ������������֮��
	 * 
	 * @param nowDate
	 *            (��)
	 * @param passDate
	 *            (С)
	 * @return �����������
	 */
	public static long getDateMinusResult(Date nowDate, Date passDate) {
		long a = (nowDate.getTime() - passDate.getTime()) / (1000 * 60 * 60 * 24);
		return a;
	}

	public ActionForward getPwdCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String password = request.getParameter("password");
		JSONObject jsonObject = new JSONObject();
		if (password != null && password.trim().length() > 0) {
			password = G4Utils.encryptBasedDes(password);
			jsonObject.element("password", password);
			jsonObject.element("success", new Boolean(true));
		}
		write(jsonObject.toString(), response);
		return mapping.findForward("");
	}
}
