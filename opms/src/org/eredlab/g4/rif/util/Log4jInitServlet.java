package org.eredlab.g4.rif.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * Log4J��ʼ��
 * 
 * @author XiongChun
 * @since 2011-04-26
 */
public class Log4jInitServlet extends HttpServlet {

	/**
	 * Servlet��ʼ��
	 */
	public void init(ServletConfig config) throws ServletException {
		String root = config.getServletContext().getRealPath("/");
		String log4jLocation = config.getInitParameter("log4jLocation");
		System.setProperty("webRoot", root);
		if (G4Utils.isNotEmpty(log4jLocation)) {
			PropertyConfigurator.configure(root + log4jLocation);
		}
	}
}
