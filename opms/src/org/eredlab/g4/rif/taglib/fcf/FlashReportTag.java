package org.eredlab.g4.rif.taglib.fcf;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.tplengine.DefaultTemplate;
import org.eredlab.g4.ccl.tplengine.FileTemplate;
import org.eredlab.g4.ccl.tplengine.TemplateEngine;
import org.eredlab.g4.ccl.tplengine.TemplateEngineFactory;
import org.eredlab.g4.ccl.tplengine.TemplateType;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.rif.taglib.util.FcfConstant;
import org.eredlab.g4.rif.taglib.util.TagHelper;

/**
 * FlashReport��ǩ
 * @author XiongChun
 * @since 2010-01-30
 */
public class FlashReportTag extends TagSupport{
	private static Log log = LogFactory.getLog(FlashReportTag.class);
	private String id;
	private String type;
	private String align = "left";
	private String width= "550";
	private String height = "350"; 
	private String visible;
	private String dataVar;
	
	
	/**
	 * ��ǩ��ʼ
	 */
	public int doStartTag() throws JspException{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		String contextPath = request.getContextPath();
		Dto dto = new BaseDto();
		dto.put("contextPath", contextPath);
		dto.put("id", id);
		dto.put("align", align);
		dto.put("width", width);
		dto.put("height", height);
		dto.put("cls", visible == "true" ? "" : "x-hidden");
		dto.put("swfModelPath", contextPath + "/resource/fcf/" + FcfConstant.getReportModel(type));
		String xmlString = (String)request.getAttribute(dataVar);
		dto.put("reportXMLData", xmlString);
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
		return super.SKIP_BODY;
	}
	
	/**
	 * ��ǩ����
	 */
	public int doEndTag() throws JspException{
		return super.EVAL_PAGE;
	}
	
	/**
	 * �ͷ���Դ
	 */
	public void release(){
		this.id = null;
		this.align = null;
		this.height = null;
		this.width = null;
		this.visible = null;
		super.release();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getDataVar() {
		return dataVar;
	}

	public void setDataVar(String dataVar) {
		this.dataVar = dataVar;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}