package org.eredlab.g4.rif.resource.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.AbstractResourceServlet;
import org.eredlab.g4.rif.resource.ResourceManager;
import org.eredlab.g4.rif.resource.util.StringUtils;

/**
 * ResourceServlet
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-05-22
 */
public class ResourceServlet extends AbstractResourceServlet {

	private static final long serialVersionUID = 1L;

	private final Log logger = LogFactory.getLog(this.getClass());

	public static final String CONFIG_PARAM_KEY = "config";
	public static final String DEFAULT_CONFIG = "/WEB-INF/classes/g4.Resource.properties";

	protected ResourceManager createResourceManager(ServletConfig pServletConfig) {
		final String config = pServletConfig.getInitParameter(CONFIG_PARAM_KEY);

		String useConifg = config;

		Configuration configuration = new Configuration();
		if (StringUtils.hasLength(config) == false) {// û�����������ļ�
			logger.info("û��ָ����Դ�������������ļ�������Ĭ�ϵ�����: " + DEFAULT_CONFIG);
			useConifg = DEFAULT_CONFIG;
		}
		logger.debug("G4.Resource�����ļ���:" + useConifg);
		InputStream is = this.getServletContext().getResourceAsStream(useConifg);
		if (is == null) {
			final String MSG = "û�з��������ļ�:" + useConifg + "\n" + "ϵͳ����Ĭ�ϵ�����!";
			logger.warn(MSG);
			configuration.build();
		} else {
			try {
				configuration.buildInputStream(is);
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		ResourceManager result = configuration.buildResourceManager();
		return result;
	}

}
