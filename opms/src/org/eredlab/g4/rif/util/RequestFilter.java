package org.eredlab.g4.rif.util;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.arm.service.MonitorService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.base.IDao;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * �������ع�����
 * 
 * @author XiongChun
 * @since 2010-04-13
 */
public class RequestFilter implements Filter {

	private Log log = LogFactory.getLog(RequestFilter.class);
	protected FilterConfig filterConfig;
	protected boolean enabled;

	/**
	 * ����
	 */
	public RequestFilter() {
		filterConfig = null;
		enabled = true;
	}

	/**
	 * ��ʼ��
	 */
	public void init(FilterConfig pFilterConfig) throws ServletException {
		this.filterConfig = pFilterConfig;
		String value = filterConfig.getInitParameter("enabled");
		if (G4Utils.isEmpty(value)) {
			this.enabled = true;
		} else if (value.equalsIgnoreCase("true")) {
			this.enabled = true;
		} else {
			this.enabled = false;
		}
	}

	/**
	 * ���˴���
	 */
	public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain fc) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) pRequest;
		HttpServletResponse response = (HttpServletResponse) pResponse;
		String ctxPath = request.getContextPath();
		String requestUri = request.getRequestURI();
		String uri = requestUri.substring(ctxPath.length());
		UserInfoVo userInfo = WebUtils.getSessionContainer(request).getUserInfo();
		BigDecimal costTime = null;
		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);
		String eventMonitorEnabel = pHelper.getValue("requestMonitor", "1");
		String postType = request.getParameter("postType");
		postType = G4Utils.isEmpty(postType) ? GlobalConstants.PostType_Normal : postType;
		if (postType.equals(GlobalConstants.PostType_Nude)) {
			long start = System.currentTimeMillis();
			fc.doFilter(request, response);
			if (eventMonitorEnabel.equalsIgnoreCase(ArmConstants.EVENTMONITOR_ENABLE_Y)) {
				costTime = new BigDecimal(System.currentTimeMillis() - start);
				saveEvent(request, costTime);
			}
		} else {
			if (G4Utils.isEmpty(userInfo) && !uri.equals("/login.ered") && enabled) {
				String isAjax = request.getHeader("x-requested-with");
				if (G4Utils.isEmpty(isAjax)) {
					response.getWriter().write(
							"<script type=\"text/javascript\">parent.location.href='" + ctxPath
									+ "/login.ered?reqCode=init'</script>");
					response.getWriter().flush();
					response.getWriter().close();
				} else {
					response.sendError(GlobalConstants.Ajax_Timeout);
//                    response.setHeader("sessionstatus", "timeout");
				}
				log.warn("����:�Ƿ���URL�����ѱ��ɹ�����,�����ѱ�ǿ���ض����˵�¼ҳ��.������ԴIP����:" + request.getRemoteAddr() + " ��ͼ���ʵ�URL:"
						+ request.getRequestURL().toString() + "?reqCode=" + request.getParameter("reqCode"));
				return;
			}
			// if(){.... return;}
			long start = System.currentTimeMillis();
			fc.doFilter(request, response);
			if (eventMonitorEnabel.equalsIgnoreCase(ArmConstants.EVENTMONITOR_ENABLE_Y)) {
				costTime = new BigDecimal(System.currentTimeMillis() - start);
				saveEvent(request, costTime);
			}
		}
	}

	/**
	 * д����Ա�¼���
	 * 
	 * @param request
	 */
	private void saveEvent(HttpServletRequest request, BigDecimal costTime) {
		UserInfoVo userInfo = WebUtils.getSessionContainer(request).getUserInfo();
		if (G4Utils.isEmpty(userInfo)) {
			return;
		}
		String menuid = request.getParameter("menuid4Log");
		Dto dto = new BaseDto();
		dto.put("account", userInfo.getAccount());
		dto.put("activetime", G4Utils.getCurrentTime());
		dto.put("userid", userInfo.getUserid());
		dto.put("username", userInfo.getUsername());
		dto.put("requestpath", request.getRequestURI());
		dto.put("methodname", request.getParameter("reqCode"));
		dto.put("eventid", IDHelper.getEventID());
		dto.put("costtime", costTime);
		if (G4Utils.isNotEmpty(menuid)) {
			IDao g4Dao = (IDao) SpringBeanLoader.getSpringBean("g4Dao");
			String menuname = ((BaseDto) g4Dao.queryForObject("queryEamenuByMenuID", menuid)).getAsString("menuname");
			String msg = userInfo.getUsername() + "[" + userInfo.getAccount() + "]���˲˵�[" + menuname + "]";
			dto.put("description", msg);
			log.info(msg);
		} else {
			String msg = userInfo.getUsername() + "[" + userInfo.getAccount() + "]������Action����["
					+ request.getParameter("reqCode") + "]";
			dto.put("description", msg);
			log.info(msg + ";����·��[" + request.getRequestURI() + "]");
		}
		MonitorService monitorService = (MonitorService) SpringBeanLoader.getSpringBean("monitorService");
		monitorService.saveEvent(dto);

	}

	/**
	 * ����
	 */
	public void destroy() {
		filterConfig = null;
	}

}
