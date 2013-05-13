package org.eredlab.g4.ccl.properties;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * Properties�ļ���̬����
 * 
 * @author XiongChun
 * @since 2009-08-2
 */
public class PropertiesFactory {
	private static Log log = LogFactory.getLog(PropertiesFactory.class);
	/**
	 * �����ļ�ʵ������
	 */
	private static Dto container = new BaseDto();
	
	static{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = PropertiesFactory.class.getClassLoader();
				}
			//���������ļ�global.eredbos.properties
			try {
			  InputStream is = classLoader.getResourceAsStream("global.g4.properties");
			  PropertiesHelper ph = new PropertiesHelper(is);
			  container.put(PropertiesFile.G4, ph);
			  } catch (Exception e1) {
			  log.error(GlobalConstants.Exception_Head + "���������ļ�global.g4.properties����!");
			  e1.printStackTrace();
			  }
		     //���������ļ�global.myconfig.properties
			 try {
				InputStream is = classLoader.getResourceAsStream("global.app.properties");
				PropertiesHelper ph = new PropertiesHelper(is);
				container.put(PropertiesFile.APP, ph);
			 } catch (Exception e1) {
				log.error(GlobalConstants.Exception_Head + "���������ļ�global.app.properties����!");
				e1.printStackTrace();
			}
			 try {
				InputStream is = classLoader.getResourceAsStream("socket.properties");
				PropertiesHelper ph = new PropertiesHelper(is);
				container.put(PropertiesFile.SOCKET, ph);
			 } catch (Exception e1) {
				log.error(GlobalConstants.Exception_Head + "���������ļ�global.app.properties����!");
				e1.printStackTrace();
			}
	}
	
    /**
     * ��ȡ�����ļ�ʵ��
     * @param pFile �ļ�����
     * @return ���������ļ�ʵ��
     */
	public static PropertiesHelper getPropertiesHelper(String pFile){
		PropertiesHelper ph = (PropertiesHelper)container.get(pFile);
		return ph;
	}
}
