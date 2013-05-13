package org.eredlab.g4.rif.taglib.html;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.util.WebUtils;

/**
 * Out��ǩ<br>
 * ���request,session,application������ı���ֵ
 * @author XiongChun
 * @since 2010-02-30
 */
public class OutTag extends TagSupport {
	
	private static Log log = LogFactory.getLog(OutTag.class);
	private String scope;
	private String key;
	
	/**
	 * ��ǩ��ʼ
	 */
	public int doStartTag() throws JspException{
		scope = G4Utils.isEmpty(scope) ? "request" : scope;
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		String valueString = "";
		if(scope.equalsIgnoreCase("request")){
			valueString = (String)request.getAttribute(key);
		}else if(scope.equalsIgnoreCase("session")){
			valueString = (String)WebUtils.getSessionAttribute(request, key);
		}else if (scope.equalsIgnoreCase("application")) {
			valueString = (String)request.getSession().getServletContext().getAttribute(key);
		}
		try {
			pageContext.getOut().write(valueString);
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
		scope = null;
		key = null;
		super.release();
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
