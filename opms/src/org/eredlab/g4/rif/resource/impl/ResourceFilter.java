package org.eredlab.g4.rif.resource.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.AbstractResourceFilter;
import org.eredlab.g4.rif.resource.ResourceManager;
import org.eredlab.g4.rif.resource.util.StringUtils;

/**
 * ResourceFilter
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-05-22
 */
public class ResourceFilter extends AbstractResourceFilter {

	private static final long serialVersionUID = 1L;

	private final Log logger = LogFactory.getLog(this.getClass());

	public static final String CONFIG_PARAM_KEY = "config";

	protected ResourceManager createResourceManager(FilterConfig pFilterConfig) {
		String value = pFilterConfig.getInitParameter("enabled");

		String config = pFilterConfig.getInitParameter(CONFIG_PARAM_KEY);

		Configuration configuration = new Configuration();
		if (StringUtils.hasLength(config)) {
			logger.debug("G4.Resource�����ļ���:" + config);
		}
		java.util.Properties sysProperties = new java.util.Properties();
		// װ���û�ָ������Դ
		java.util.Properties configProperties = new java.util.Properties();
		if (StringUtils.hasLength(config)) {
			InputStream is = pFilterConfig.getServletContext().getResourceAsStream(config);
			if (is != null) {
				try {
					configProperties.load(is);
				} catch (IOException ex) {
					final String msg = "װ��������Դ:" + config + "ʧ��!";
					logger.error(msg, ex);
					throw new java.lang.RuntimeException(msg, ex);
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
						logger.error("�ر�������ʧ��!", e);
					}
				}
			}
		}

		if (configProperties.isEmpty()) {
			java.io.InputStream defaultIS = ResourceFilter.class.getResourceAsStream("G4.DefaultResource.properties");
			if (defaultIS != null) {
				try {
					configProperties.load(defaultIS);
				} catch (IOException ex) {
					final String msg = "װ��ϵͳ��Դ:" + "G4.DefaultResource.properties" + "ʧ��!";
					logger.error(msg, ex);
					throw new java.lang.RuntimeException(msg, ex);
				} finally {
					try {
						defaultIS.close();
					} catch (IOException e) {
						e.printStackTrace();
						logger.error("�ر�������ʧ��!", e);
					}
				}
			}
		}
		sysProperties.putAll(configProperties);
		configuration.buildProperties(sysProperties);
		ResourceManager result = configuration.buildResourceManager();
		return result;
	}

	public static void main(String[] args) {
		Pattern p = Pattern.compile("(url(\\p{Blank})*)(\\()(([^\\)])*)(\\))");
		Matcher m = p
				.matcher(".x-tip-br{background: url  ( ../images/default/form/error-tip-corners.gif  ) no-repeat right -6px;}");
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String x = m.group(4).trim() + "?timestamp=13";
			m.appendReplacement(sb, "$1$3" + x + "$6");
		}
		m.appendTail(sb);
		System.err.println(sb.toString());
	}

}
