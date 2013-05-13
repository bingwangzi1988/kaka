package org.eredlab.g4.rif.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.arm.service.MonitorService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.bmf.base.IDao;
import org.eredlab.g4.bmf.base.IReader;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.springframework.context.ApplicationContext;

/**
 * ϵͳ����������
 * 
 * @author XiongChun
 * @since 2010-04-13
 */
public class SystemInitListener implements ServletContextListener {
	private static Log log = LogFactory.getLog(SystemInitListener.class);
	private boolean success = true;
	private ApplicationContext wac = null;

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		systemStartup(sce.getServletContext());
	}

	/**
	 * Ӧ��ƽ̨����
	 */
	private void systemStartup(ServletContext servletContext) {
		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);
		String forceLoad = pHelper.getValue("forceLoad", ArmConstants.FORCELOAD_N);
		long start = System.currentTimeMillis();
		if (forceLoad.equalsIgnoreCase(ArmConstants.FORCELOAD_N)) {
			System.out.println("********************************************");
			System.out.println("���Խ���ϵͳ������Ӧ�ÿ���ƽ̨��ʼ����...");
			System.out.println("********************************************");
		}
		try {
			wac = SpringBeanLoader.getApplicationContext();
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		if (success) {
			MonitorService monitorService = (MonitorService) SpringBeanLoader.getSpringBean("monitorService");
			monitorService.deleteHttpSession(new BaseDto());
			try {
				initDbType();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (success) {
			System.out.println("-------------------------------");
			System.out.println("ϵͳ��ʼ�����ֵ�װ�س���...");
			System.out.println("��ʼ�����ֵ�...");
			IReader g4Reader = (IReader) SpringBeanLoader.getSpringBean("g4Reader");
			List codeList = null;
			try {
				codeList = g4Reader.queryForList("getCodeViewList");
				System.out.println("�ֵ���سɹ�!");
			} catch (Exception e) {
				success = false;
				System.out.println("�ֵ����ʧ��!");
				e.printStackTrace();
			}
			servletContext.setAttribute("EACODELIST", codeList);
		}
		if (success) {
			System.out.println("-------------------------------");
			System.out.println("ϵͳ��ʼ����ȫ�ֲ�����װ�س���...");
			System.out.println("��ʼ����ȫ�ֲ�����...");
			List paramList = null;
			try {
				IReader g4Reader = (IReader) SpringBeanLoader.getSpringBean("g4Reader");
				paramList = g4Reader.queryForList("getParamList");
				System.out.println("ȫ�ֲ�������سɹ�!");
			} catch (Exception e) {
				success = false;
				System.out.println("ȫ�ֲ��������ʧ��!");
				e.printStackTrace();
			}
			servletContext.setAttribute("EAPARAMLIST", paramList);
		}
		long timeSec = (System.currentTimeMillis() - start) / 1000;
		System.out.println("********************************************");
		if (success) {
			System.out.println("���Խ���ϵͳ������Ӧ�ÿ���ƽ̨�����ɹ�[" + G4Utils.getCurrentTime() + "]");
			System.out.println("�����ܺ�ʱ: " + timeSec / 60 + "�� " + timeSec % 60 + "�� ");
		} else {
			System.out.println("���Խ���ϵͳ������Ӧ�ÿ���ƽ̨����ʧ��[" + G4Utils.getCurrentTime() + "]");
			System.out.println("�����ܺ�ʱ: " + timeSec / 60 + "��" + timeSec % 60 + "��");
		}
		System.out.println("********************************************");
	}

	/**
	 * ʶ��JDBC��������
	 * 
	 * @throws SQLException
	 */
	private void initDbType() throws SQLException {
		IDao g4Dao = (IDao) SpringBeanLoader.getSpringBean("g4Dao");
		Connection connection = g4Dao.getConnection();
		if (connection.getMetaData().getDatabaseProductName().toLowerCase().indexOf("ora") > -1) {
			System.setProperty("eRedg4.JdbcType", "oracle");
		} else if (connection.getMetaData().getDatabaseProductName().toLowerCase().indexOf("mysql") > -1) {
			System.setProperty("eRedg4.JdbcType", "mysql");
		} else {
			if (log.isErrorEnabled()) {
				log.error(GlobalConstants.Exception_Head + "ƽ̨Ŀǰ����֧����ʹ�õ����ݿ��Ʒ.������֧��,���������ϵ!");
			}
			System.exit(0);
		}
	}
}
