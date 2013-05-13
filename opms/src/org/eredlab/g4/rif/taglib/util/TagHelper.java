package org.eredlab.g4.rif.taglib.util;

import javax.servlet.jsp.tagext.BodyContent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.actions.DispatchAction;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * JSP�Զ����ǩ(eRedUI)�ڲ�ʹ�õĸ���������
 * @author XiongChun
 * @since 2010-01-23
 * @see DispatchAction
 */
public class TagHelper {
	private static Log log = LogFactory.getLog(TagHelper.class);
	
	/**
	 * ��ȡģ��·��
	 * @param pPath ��ǩʵ�����Java��·��
	 * @return ����ģ��·��
	 */
	public static String getTemplatePath(String pPath){
		if(G4Utils.isEmpty(pPath))
			return "";
		String templatePath = "";
		String path = pPath.replace('.', '/');
		String packageUnits[] = path.split("/");
		String className = packageUnits[packageUnits.length - 1];
		templatePath = path.substring(0, path.length() - className.length());
		templatePath += "template/" + className + ".tpl";
		log.debug("ģ���ļ�·��:" + templatePath);
		return templatePath;
	}
	
	/**
	 * ��ȡģ��·��
	 * @param pPath ��ǩʵ�����Java��·��
	 * @return ����ģ��·��
	 */
	public static String getTemplatePath(String pPath,String pFileName){
		if(G4Utils.isEmpty(pPath))
			return "";
		String templatePath = "";
		String path = pPath.replace('.', '/');
		String packageUnits[] = path.split("/");
		String className = packageUnits[packageUnits.length - 1];
		templatePath = path.substring(0, path.length() - className.length());
		templatePath += "template/" + pFileName;
		log.debug("ģ���ļ�·��:" + templatePath);
		return templatePath;
	}
	
	/**
	 * ��BodyContent���и�ʽ����
	 * @param pBodyContent �����BodyContent����
	 * @return ���ش�����BodyContent�ַ�������
	 */
	public static String formatBodyContent(BodyContent pBodyContent){
		if(G4Utils.isEmpty(pBodyContent))
			return "";
		return pBodyContent.getString().trim();
	}
	
	/**
	 * ���ַ���ģ���е������ַ����д���
	 * @param pStr ������ַ���ģ��
	 * @return ���ش������ַ���
	 */
	public static String replaceStringTemplate(String pStr){
		if(G4Utils.isEmpty(pStr))
			return "";
		pStr = pStr.replace('*','\"');

		return pStr;
	}
	
	/**
	 * ��ģ���ַ��ͱ������п�У��
	 * @param pString
	 * @return
	 */
	public static String checkEmpty(String pString){
		return G4Utils.isEmpty(pString) ? TagConstant.Tpl_Out_Off : pString;
	}
}
