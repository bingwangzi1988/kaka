package org.eredlab.g4.rif.resource.loader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.HttpHolder;
import org.eredlab.g4.rif.resource.LoadResoruceException;
import org.eredlab.g4.rif.resource.Resource;
import org.eredlab.g4.rif.resource.support.DefaultResource;

/**
 * FileResourceLoader
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-08-3
 */
public class FileResourceLoader extends AbstractResourceLoader {

	private final Log logger = LogFactory.getLog(this.getClass());
	private int cacheSize;

	public FileResourceLoader() {
		cacheSize = 1024 * 2;
	}

	public FileResourceLoader(int pCacheSize) {
		cacheSize = pCacheSize;
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
		String uri = urlMapping(pUri);
		if (logger.isDebugEnabled()) {
			logger.debug("����װ����Դ�ļ�:" + uri + " ...");
		}

		InputStream is = HttpHolder.getServletContext().getResourceAsStream(uri);
		if (is == null) {
			final String MSG = "û���ҵ���Դ�ļ�:" + uri;
			logger.error(MSG);
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
		result.setLastModified(getFileLastModified(uri));
		return result;
	}

	private long getFileLastModified(String pUri) {
		if (pUri == null) {
			final String MSG = "��ԴURIΪ��";
			throw new NullPointerException(MSG);
		}
		final String filePath = HttpHolder.getServletContext().getRealPath(pUri);
		if (filePath == null) {
			return 0;
		}
		File file = new File(filePath);
		if (file.canRead()) {
			return file.lastModified();
		} else {
			return 0;
		}
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public long getLastModified(String pUri) {
		if (pUri == null) {
			throw new NullPointerException("��ԴURIΪ��");
		}
		String uri = urlMapping(pUri);
		return getFileLastModified(uri);
	}

}
