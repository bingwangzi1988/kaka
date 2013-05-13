package org.eredlab.g4.rif.taglib.html;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.arm.service.ArmTagSupportService;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;
import org.eredlab.g4.ccl.tplengine.DefaultTemplate;
import org.eredlab.g4.ccl.tplengine.FileTemplate;
import org.eredlab.g4.ccl.tplengine.TemplateEngine;
import org.eredlab.g4.ccl.tplengine.TemplateEngineFactory;
import org.eredlab.g4.ccl.tplengine.TemplateType;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.rif.taglib.util.TagConstant;
import org.eredlab.g4.rif.taglib.util.TagHelper;
import org.eredlab.g4.rif.util.WebUtils;

/**
 * HTML��ǩ
 * @author XiongChun
 * @since 2010-01-30
 */
public class HtmlTag extends TagSupport{
	
	private ArmTagSupportService armTagSupportService = (ArmTagSupportService)SpringBeanLoader.getSpringBean("armTagSupportService");
	
	private static Log log = LogFactory.getLog(HtmlTag.class);
	private String extDisabled;
	private String title;
	private String jqueryEnabled;
	private String showLoading;
	private String uxEnabled = "true";
	private String fcfEnabled = "false";
	private String doctypeEnable;  //����ʱ����ѡ��Ŀؼ���ҳ����Ҫ����Ϊ:true
	private String exportParams = "false";
	
	/**
	 * ��ǩ��ʼ
	 */
	public int doStartTag() throws JspException{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		String contextPath = request.getContextPath();
		request.setAttribute("webContext", contextPath);
		Dto dto = new BaseDto();
		dto.put("ajaxErrCode", GlobalConstants.Ajax_Timeout);
		dto.put("requestURL", request.getRequestURL());
		dto.put("contextPath", contextPath);
		dto.put("doctypeEnable", doctypeEnable);
		dto.put("extDisabled", G4Utils.isEmpty(extDisabled) ? "false" : extDisabled);
		dto.put("title", G4Utils.isEmpty(title) ? "eRedG4" : title);
		dto.put("jqueryEnabled", G4Utils.isEmpty(jqueryEnabled) ? "false" : jqueryEnabled);
		dto.put("showLoading", G4Utils.isEmpty(showLoading) ? "true" : showLoading);
		dto.put("uxEnabled", uxEnabled);
		dto.put("fcfEnabled", fcfEnabled);
		dto.put("exportParams", exportParams);
		dto.put("pageLoadMsg", WebUtils.getParamValue("PAGE_LOAD_MSG", request));
		if (exportParams.equals("true")) {
			dto.put("paramList", WebUtils.getParamList(request));
		}
		String agent = request.getHeader("user-agent");
		dto.put("firefox", agent.indexOf("Firefox") == -1 ? "false" : "true");
		PropertiesHelper p = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);
		dto.put("extMode", p.getValue("extMode", TagConstant.Ext_Mode_Run));
		dto.put("runMode", p.getValue("runMode", TagConstant.RUN_MODE_NORMAL));
		Dto themeDto = new BaseDto();
		UserInfoVo userInfo = WebUtils.getSessionContainer(request).getUserInfo();
		Dto resultDto = new BaseDto();
		if(G4Utils.isNotEmpty(userInfo)){
			themeDto.put("userid", userInfo.getUserid());
			resultDto = armTagSupportService.getEauserSubInfo(themeDto);
		}
		String theme = null;
		if(G4Utils.isNotEmpty(resultDto))
			theme = resultDto.getAsString("theme");
		theme = G4Utils.isEmpty(theme) ? WebUtils.getParamValue("SYS_DEFAULT_THEME", request) : theme;
		dto.put("theme", theme);
		TemplateEngine engine = TemplateEngineFactory.getTemplateEngine(TemplateType.VELOCITY);
		DefaultTemplate template = new FileTemplate();
		template.setTemplateResource(TagHelper.getTemplatePath(getClass().getName()));
		StringWriter writer = engine.mergeTemplate(template, dto);
		try {
			pageContext.getOut().write(writer.toString());
		} catch (IOException e) {
			log.error(GlobalConstants.Exception_Head + e.getMessage());
			e.printStackTrace();
		}
		return super.EVAL_BODY_INCLUDE;
	}
	
	/**
	 * ��ǩ����
	 */
	public int doEndTag() throws JspException{
		try {
			pageContext.getOut().write("</html>");
		} catch (IOException e) {
			log.error(GlobalConstants.Exception_Head + e.getMessage());
			e.printStackTrace();
		}
		return super.EVAL_PAGE;
	}
	
	/**
	 * �ͷ���Դ
	 */
	public void release(){
		extDisabled = null;
		title = null;
		jqueryEnabled = null;
		uxEnabled = null;
		fcfEnabled = null;
		doctypeEnable = null;
		exportParams = null;
		super.release();
	}

	public void setExtDisabled(String extDisabled) {
		this.extDisabled = extDisabled;
	}

	public void setJqueryEnabled(String jqueryEnabled) {
		this.jqueryEnabled = jqueryEnabled;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setShowLoading(String showLoading) {
		this.showLoading = showLoading;
	}

	public void setUxEnabled(String uxEnabled) {
		this.uxEnabled = uxEnabled;
	}

	public String getFcfEnabled() {
		return fcfEnabled;
	}

	public void setFcfEnabled(String fcfEnabled) {
		this.fcfEnabled = fcfEnabled;
	}

	public void setDoctypeEnable(String doctypeEnable) {
		this.doctypeEnable = doctypeEnable;
	}

	public void setExportParams(String exportParams) {
		this.exportParams = exportParams;
	}
}
