package org.eredlab.g4.bmf.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SpringBwan������<br>
 * (1)��ʹ�ô˼��������Ի��һ��Spring������ApplicationContextʵ��,ͨ����ʵ����Ϳ��Է����ʹ��getBean()
 * ������ȡSpringBean.<br>
 * (2)����Ҳ����ֱ��ͨ�������ṩ��getSpringBean()�������SpringBean��
 * 
 * @author XiongChun
 * @since 2009-07-22
 */
public class SpringBeanLoader{
	private static Log log = LogFactory.getLog(SpringBeanLoader.class);
	private static ApplicationContext applicationContext;

	static {
		try {
			initApplicationContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʼ��ApplicationContext����
	 * @throws Exception 
	 */
	private static void initApplicationContext() throws Exception {
	    PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);
	    String forceLoad = pHelper.getValue("forceLoad", ArmConstants.FORCELOAD_N);
		try {
			if (forceLoad.equalsIgnoreCase(ArmConstants.FORCELOAD_N)) {
				System.out.println("ϵͳ���ڳ�ʼ����������...");
			}
			applicationContext = new ClassPathXmlApplicationContext(new String[] { "config\\global.config.xml" });
			if (forceLoad.equalsIgnoreCase(ArmConstants.FORCELOAD_N)) {
				System.out.println("������ʼ���ɹ����������й�Bean�Ѿ���ʵ������");
			}
		} catch (Exception e) {
			System.out.println("����������ʼ��ʧ��.");
			log.error(GlobalConstants.Exception_Head + "��ʼ������������������,����ϸ������������ļ��!\n" + e.getMessage());
			e.printStackTrace();
			System.exit(0);
			throw e;
		}
	}

	/**
	 * ����ApplicationContext����
	 * 
	 * @return ApplicationContext ���ص�ApplicationContextʵ��
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * ��ȡһ��SpringBean����
	 * 
	 * @param pBeanId
	 *            Spring�����ļ��������õ�SpringID��
	 * @return Object ���ص�SpringBeanʵ��
	 */
	public static Object getSpringBean(String pBeanId) {
		Object springBean = null;
		try {
			springBean = applicationContext.getBean(pBeanId);
		} catch (NoSuchBeanDefinitionException e) {
			log.error(GlobalConstants.Exception_Head + "Spring�����ļ���û��ƥ�䵽ID��Ϊ:[" + pBeanId + "]��SpringBean���,����!");
			log.error(e.getMessage());
		}
		return springBean;
	}
	
	/**
	 * add by haoxiaofeng
	 * @param event
	 */
	public static void publishEvent(ApplicationEvent event){
		applicationContext.publishEvent(event);
	}
}
