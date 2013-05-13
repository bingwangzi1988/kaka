package org.eredlab.g4.rif.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.util.ResourceWebUtils;

/**
 * AbstractResourceServlet
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-11-01
 */
public abstract class AbstractResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final long ONE_YEAR = 31536000000L;
	private static final long TEN_YEARS = ONE_YEAR * 10L;
	private final Log logger = LogFactory.getLog(this.getClass());

	private ResourceManager resourceManager;

	protected void doPost(HttpServletRequest pRequest, HttpServletResponse pResponse) throws ServletException,
			IOException {
		doGet(pRequest, pResponse);
	}

	protected void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse) throws ServletException,
			IOException {
		try {
			HttpHolder.setRequest((HttpServletRequest) pRequest);
			HttpHolder.setResponse((HttpServletResponse) pResponse);
			handle(pRequest, pResponse);
		} finally {
			HttpHolder.setRequest(null);
			HttpHolder.setResponse(null);
		}
	}

	private void handle(HttpServletRequest pRequest, HttpServletResponse pResponse) throws ServletException,
			IOException {

		final String contextPath = pRequest.getContextPath();
		final String uri = pRequest.getRequestURI().substring(contextPath.length());
		Resource res = resourceManager.get(uri);
		// header ��Ϣ��������������Ŀ:http://sourceforge.net/projects/packtag
		// It won't be cached by proxies, because of the Cache-Control: private
		// header. Let the Browser cache the resource
		pResponse.setHeader("Cache-Control", "private");

		// Don't let the resource expire
		pResponse.setDateHeader("Expires", new Date().getTime() + TEN_YEARS); // Expires
																				// after
																				// ten
																				// year

		// Funny header, see here: http://en.wikipedia.org/wiki/HTTP_ETag
		pResponse.setHeader("ETag", "G4" + Long.toString(res.getLastModified()));
		pResponse.setHeader("power-by", "G4");
		// Check if the "If_None-Match" Header is send. When this equals to the
		// resource, it is not
		// modified, and hasn't to be delivered again (Status code 304: Not
		// modifed).
		// This happens when someone e.g. has the resource allready loaded, and
		// presses the reload-button.
		// (Doesn't seems to work on IE6, like always)
		StringBuffer selfMatch = new StringBuffer();
		selfMatch.append("G4");
		selfMatch.append(res.getLastModified());

		if (selfMatch.toString().equals(pRequest.getHeader("If-None-Match"))) {
			logger.debug("��Դ:" + uri + "δ�����仯,ֱ��ʹ�ÿͻ���cache����!");
			pResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			pResponse.setHeader("Last-Modified", pRequest.getHeader("If-Modified-Since"));

		} else {// ��������cache
			final String contextType = res.getMimeType();
			if (contextType != null) {
				pResponse.setContentType(contextType);
			}
			final String charset = res.getCharset();
			if (charset != null) {
				pResponse.setCharacterEncoding(charset);
			}

			Date lastModified = new Date();
			pResponse.setDateHeader("Last-Modified", lastModified.getTime());

			OutputStream out = pResponse.getOutputStream();

			/**
			 * ע��:�����������gzip�ж�,��Ϊ���ݽ���gzip cache����,�����еĿͻ���֧��,�еĲ�֧��gzip ����ÿ�ζ���Ҫ�ж�.
			 */
			if (ResourceWebUtils.isSupportedGzip(pRequest)) {// �ͻ���֧��gzipѹ��
				if (res.isGzip()) {// �Ѿ���gzipѹ��
					ResourceWebUtils.setGzipHeader(pResponse);// ����gzipͷ
					out.write(res.getTreatedData());
				} else {
					out.write(res.getTreatedData());
				}
			} else {// ��֧��gzip
				if (res.isGzip()) {// ��ѹ��������ԭʼ����
					out.write(res.getData());
				} else {// δѹ��������ʹ�ô���������ݣ���������д����֮��Ĵ���
					out.write(res.getTreatedData());
				}
			}
			out.flush();
		}

	}

	protected abstract ResourceManager createResourceManager(ServletConfig pServletConfig);

	public void destroy() {
		super.destroy();
		resourceManager.destroy();
		HttpHolder.setServletContext(null);
	}

	public void init(ServletConfig pServletConfig) throws ServletException {
		super.init(pServletConfig);
		logger.info("���ڴ���G4.Resource��̬��Դ������...");
		resourceManager = createResourceManager(pServletConfig);
		resourceManager.init();
		logger.info("����G4.Resource��̬��Դ�������ɹ�.");
	}

	public void init() throws ServletException {
		super.init();
		HttpHolder.setServletContext(this.getServletContext());
	}

}
