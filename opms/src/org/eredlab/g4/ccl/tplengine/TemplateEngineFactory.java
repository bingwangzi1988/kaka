package org.eredlab.g4.ccl.tplengine;

import java.util.HashMap;
import java.util.Map;

import org.eredlab.g4.ccl.tplengine.velocity.VelocityTemplateEngine;
import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * ģ�����湤��
 * @author XiongChun
 * @since 2009-07-26
 */
public class TemplateEngineFactory {
	/**
	 * ��������
	 */
	private static Map ENGINES = new HashMap();
	
	/**
	 * ʵ����ģ�����沢ѹ����������
	 */
	static{
		if (isExistClass("org.apache.velocity.app.VelocityEngine")){
			VelocityTemplateEngine ve = new VelocityTemplateEngine();
			ENGINES.put(TemplateType.VELOCITY, ve);
		}else{
			//todo ֧������ģ��������չ
		}
	}
	
	/**
	 * ��鵱ǰClassLoader��,�Ƿ����ָ��class
	 * @param pClass
	 * @return
	 */
	private static boolean isExistClass(String pClass) {
		try {
			Class.forName(pClass);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * ��ȡģ������ʵ��
	 * @param pTemplateType ��������
	 * @return ����ģ������ʵ��
	 */
	public static TemplateEngine getTemplateEngine(TemplateType pType) {
		if (pType == null) {
			return null;
		}
		if (ENGINES.containsKey(pType) == false) {
			throw new IllegalArgumentException(GlobalConstants.Exception_Head + "��֧�ֵ�ģ�����:" + pType.getType());
		}
		return (TemplateEngine) ENGINES.get(pType);
	}
}
