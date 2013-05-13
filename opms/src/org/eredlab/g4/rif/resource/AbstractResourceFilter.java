package org.eredlab.g4.rif.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.util.ResourceWebUtils;

/**
 * AbstractResourceFilter
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-11-01
 */
public abstract class AbstractResourceFilter implements Filter {

	private static final long serialVersionUID = 1L;

	private final Log logger = LogFactory.getLog(this.getClass());
	private static final long ONE_YEAR = 31536000000L;
	private static final long TEN_YEARS = ONE_YEAR * 10L;

	private ResourceManager resourceManager;

	public void destroy() {
		resourceManager.destroy();
		HttpHolder.setServletContext(null);
	}

	public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pFilterChain)
			throws IOException, ServletException {
		try {
			HttpHolder.setRequest((HttpServletRequest) pRequest);
			HttpHolder.setResponse((HttpServletResponse) pResponse);
			HttpHolder.setFilterChain(pFilterChain);
			executeFilter(pRequest, pResponse, pFilterChain);
		} finally {
			HttpHolder.setRequest(null);
			HttpHolder.setResponse(null);
			HttpHolder.setFilterChain(null);
		}

	}

	protected void executeFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pFilterChain)
			throws IOException, ServletException {
		handle((HttpServletRequest) pRequest, (HttpServletResponse) pResponse, pFilterChain);
	}

	private String newURI(String pUri) {
		if (pUri == null) {
			return null;
		}
		for (int i = 0; i < oldPrefixs.size(); i++) {
			String oldPrefix = (String) oldPrefixs.get(i);
			if (pUri.startsWith(oldPrefix)) {
				String newPrefix = (String) newPrefixs.get(i);
				if (newPrefix == null) {
					continue;
				}
				return newPrefix + pUri.substring(oldPrefix.length());
			}
		}
		return pUri;
	}

	private void handle(HttpServletRequest pRequest, HttpServletResponse pResponse, FilterChain pFilterChain)
			throws ServletException, IOException {
		final String contextPath = pRequest.getContextPath();
		final String uri = pRequest.getRequestURI().substring(contextPath.length());

        //����gzipѹ����ehcache����
		Resource res = resourceManager.get(uri);
		// ˵��û���ҵ���Ӧ����Դװ������
		if (res == null) {
			pFilterChain.doFilter(pRequest, pResponse);
			return;
		}
		// header ��Ϣ��������������Ŀ:http://sourceforge.net/projects/packtag
		// It won't be cached by proxies, because of the Cache-Control: private
		// header. Let the Browser cache the resource
		// / pResponse.setHeader("Cache-Control", "private");
		//
		// // Don't let the resource expire
		// pResponse.setDateHeader("Expires", new Date().getTime() + TEN_YEARS);
		pResponse.setDateHeader("Expires", 0);

		// Funny header, see here: http://en.wikipedia.org/wiki/HTTP_ETag
		// ������������
		String token = '"' + res.getResourceCode() + '"';
		pResponse.setHeader("ETag", token);
		pResponse.setHeader("power-by", "G4");

		String ifNoneMatch = pRequest.getHeader("If-None-Match");
		String ifModifiedSince = pRequest.getHeader("If-Modified-Since");

		if (logger.isDebugEnabled()) {
			logger.debug("ifNoneMatch=" + ifNoneMatch);
			logger.debug("ifModifiedSince=" + ifModifiedSince);
		}

		boolean isNotModified = false;
		if (token.equals(ifNoneMatch)) {// etagƥ���ˣ�����û�޸�
			isNotModified = true;
		} else {
			// Ϊ0��ʱ��Ϊ�޸��ˣ�
			if (res.getLastModified() != 0) {// ���ʱ���Ƿ�ƥ�䣬����޸�ʱ�䲻��0ʱ����Ϊ��0ʱ���޷��ж��Ƿ��޸���
				long lastModified = pRequest.getDateHeader("If-Modified-Since");
				if (lastModified == res.getLastModified()) {
					isNotModified = true;
				} else {
					isNotModified = false;
				}
			}
		}

		/**
		 * FIXME: IE6ʱ,������gzipѹ��ʱ,pRequest.getHeader("If-None-Match")����ֵ��null,
		 * ������ÿ�ζ��������ļ�. ���ܱ��־Ͳ���.
		 */
		if (isNotModified) {
			logger.debug("��Դ:" + uri + "δ�����仯,ֱ��ʹ�ÿͻ���cache����!");
			pResponse.sendError(HttpServletResponse.SC_NOT_MODIFIED);
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
			// Calendar cal = Calendar.getInstance();
			// cal.set(Calendar.MILLISECOND, 0);
			// Date lastModified = cal.getTime();
			pResponse.setDateHeader("Last-Modified", res.getLastModified());

			OutputStream out = pResponse.getOutputStream();

			/**
			 * ע��:�����������gzip�ж�,��Ϊ���ݽ���gzip cache����,�����еĿͻ���֧��,�еĲ�֧��gzip ����ÿ�ζ���Ҫ�ж�.
			 * ie6�²�����gzipѹ��.
			 */
			boolean isSupportedGzip = ResourceWebUtils.isSupportedGzip(pRequest);
			if (isSupportedGzip) {
				if (ResourceWebUtils.isIE6(pRequest)) {
					isSupportedGzip = res.getLastModified() != 0;// ������0ʱ����֧��gzipѹ��,��Ϊ�������ֻ��
					// ͨ��etag���ж��ļ��Ƿ��޸��ˣ�����ie6����gzipѹ��ʱ
					// etagֵ�ᶪʧ.
				}
			}
			if (isSupportedGzip) {// �ͻ���֧��gzipѹ��
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

	public static final String PREFIX_MAPPING_PARAM_KEY = "prefixMapping";
	private List oldPrefixs = new ArrayList();
	private List newPrefixs = new ArrayList();

	private FilterConfig filterConfig = null;

	protected abstract ResourceManager createResourceManager(FilterConfig pFilterConfig);

	public void init(FilterConfig pFilterConfig) throws ServletException {
		this.filterConfig = pFilterConfig;
		HttpHolder.setServletContext(pFilterConfig.getServletContext());
		logger.info("���ڴ���G4.Resource��̬��Դ������...");
		resourceManager = createResourceManager(pFilterConfig);
		resourceManager.init();
		logger.info("����G4.Resource��̬��Դ�������ɹ�.");

	}

}
