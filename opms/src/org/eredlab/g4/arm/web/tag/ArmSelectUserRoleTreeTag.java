package org.eredlab.g4.arm.web.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.arm.web.tag.vo.MenuVo;
import org.eredlab.g4.bmf.base.IDao;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.tplengine.DefaultTemplate;
import org.eredlab.g4.ccl.tplengine.FileTemplate;
import org.eredlab.g4.ccl.tplengine.TemplateEngine;
import org.eredlab.g4.ccl.tplengine.TemplateEngineFactory;
import org.eredlab.g4.ccl.tplengine.TemplateType;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.rif.taglib.util.TagHelper;
import org.eredlab.g4.rif.util.WebUtils;

/**
 * ArmSelectUserRoleTreeTag��ǩ:eRedG4_ARMר��
 * @author zly
 * @since 2012-06-07
 */
public class ArmSelectUserRoleTreeTag extends TagSupport {
	private static Log log = LogFactory.getLog(ArmSelectUserRoleTreeTag.class);
	
	/**
	 * ��ǩ��ʼ
	 */
	public int doStartTag() throws JspException{
		IDao g4Dao = (IDao)SpringBeanLoader.getSpringBean("g4Dao");
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		Dto grantDto = new BaseDto();
		grantDto.put("userid", request.getParameter("userid"));
		List grantedList = g4Dao.queryForList("queryGrantedUserRoleMenusByUserId", grantDto);
		List menuList = new ArrayList();
		String account = WebUtils.getSessionContainer(request).getUserInfo().getAccount();
		String developerAccount = WebUtils.getParamValue("DEFAULT_DEVELOP_ACCOUNT", request);
		String superAccount = WebUtils.getParamValue("DEFAULT_ADMIN_ACCOUNT", request);
		if (account.equalsIgnoreCase(developerAccount) || account.equalsIgnoreCase(superAccount)) {
			menuList = g4Dao.queryForList("queryMenusForUserGrant");
		}else {
			String userid = WebUtils.getSessionContainer(request).getUserInfo().getUserid();
			Dto qDto = new BaseDto();
			qDto.put("userid", userid);
			menuList = g4Dao.queryForList("queryMenusForGrant", qDto);
		}
		for(int i = 0; i < menuList.size(); i++){
			MenuVo menuVo = (MenuVo)menuList.get(i);
			if(checkGeant(grantedList, menuVo.getMenuid()).booleanValue()){
				menuVo.setChecked("true");
			}else {
				menuVo.setChecked("false");
			}
			if(menuVo.getParentid().equals("0")){
				menuVo.setIsRoot("true");
			}
			if(menuVo.getMenuid().length() < 6){
				menuVo.setExpanded("true");
			}
		}
		Dto dto = new BaseDto();
		dto.put("menuList", menuList);
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
	 * �����Ȩ
	 * @param grantList
	 * @param pMenuid
	 * @return
	 */
	private Boolean checkGeant(List grantList, String pMenuid){
		Boolean result = new Boolean(false);
		for(int i = 0; i < grantList.size(); i++){
			Dto dto = (BaseDto)grantList.get(i);
			if(pMenuid.equals(dto.getAsString("menuid"))){
				result = new Boolean(true);
			}
		}
		return result;
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
		super.release();
	}
}
