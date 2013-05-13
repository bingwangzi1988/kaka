package org.eredlab.g4.rif.resource.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.LoadResoruceException;
import org.eredlab.g4.rif.resource.Resource;
import org.eredlab.g4.rif.resource.support.DefaultResource;
import org.eredlab.g4.rif.resource.util.ClassUtils;

/**
 * ClasspathResourceLoader
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-08-1
 */
public class ClasspathResourceLoader extends AbstractResourceLoader {

	private final Log logger = LogFactory.getLog(this.getClass());
	private int cacheSize;

	public ClasspathResourceLoader() {
		cacheSize = 1024 * 2;
	}

	public ClasspathResourceLoader(int pCacheSize) {
		cacheSize = pCacheSize;
	}

	// ������Դʼ����Ϊ�ǲ����޸ĵ�
	public long getLastModified(String pUri) {
		return 1;
	}

	/**
	 * ���Ҫ����urlת���������Ǹ÷���.
	 * 
	 * @param pUri
	 * @return
	 */
	protected String urlMapping(String pUri) {
		return pUri;
	}

	public Resource load(String pUri) throws LoadResoruceException {
		if (pUri == null) {
			throw new NullPointerException("��ԴURIΪ��");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("����װ����Դ�ļ�:" + pUri + " ...");
		}

		String uri = urlMapping(pUri);
		InputStream is = ClassUtils.getResourceAsStream(this.getClass(), uri);
		if (is == null) {
			final String MSG = "û���ҵ���Դ�ļ�:" + uri;
			logger.debug(MSG);
			throw new NotFoundResourceException(MSG);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buf = new byte[cacheSize];
		int len;
		try {
			while ((len = is.read(buf)) > 0) {
				outputStream.write(buf, 0, len);
			}
		} catch (IOException e) {
			throw new LoadResoruceException("��ȡ��Դ�ļ�:" + uri + "ʧ��!", e);
		}

		byte[] data = outputStream.toByteArray();
		if (logger.isDebugEnabled()) {
			logger.debug("װ����Դ�ɹ�:" + uri);
		}
		DefaultResource result = new DefaultResource(uri, data);
		result.setLastModified(0);
		return result;

	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

}
