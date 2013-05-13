package org.eredlab.g4.rif.resource.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.Cache;
import org.eredlab.g4.rif.resource.ResourceHandler;
import org.eredlab.g4.rif.resource.ResourceLoader;
import org.eredlab.g4.rif.resource.ResourceManager;
import org.eredlab.g4.rif.resource.cache.MemoryCache;
import org.eredlab.g4.rif.resource.support.HandlerMapping;
import org.eredlab.g4.rif.resource.support.LoaderMapping;
import org.eredlab.g4.rif.resource.support.ResourceConfigMapping;
import org.eredlab.g4.rif.resource.util.AntPathMatcher;
import org.eredlab.g4.rif.resource.util.StringUtils;

/**
 * Configuration
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-06-10
 */
public class Configuration {

	private final static String DEFAULT_RESOURCE = "";

	private final Log logger = LogFactory.getLog(Configuration.class);

	private Properties properites = null;

	public Configuration() {
		properites = new Properties();
	}

	public void build() throws ConfigeException {
		this.buildResource(DEFAULT_RESOURCE);
	}

	public void buildFile(String pFile) throws ConfigeException {
		FileInputStream fileIS = null;
		try {
			fileIS = new FileInputStream(pFile);
		} catch (FileNotFoundException e) {
			final String MSG = "����Դ�ļ�:" + pFile + "ʧ��!";
			logger.error(MSG, e);
			throw new ConfigeException(MSG, e);
		}
		BufferedInputStream bufferedIS = new BufferedInputStream(fileIS);
		try {
			buildInputStream(bufferedIS);
		} finally {
			try {
				fileIS.close();
				bufferedIS.close();
			} catch (IOException e) {
				final String MSG = "�ر���Դ�ļ�:" + pFile + "ʧ��!";
				logger.warn(MSG, e);
			}

		}
	}

	public void buildResource(String pResource) throws ConfigeException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(pResource);
		BufferedInputStream bufferedIS = new BufferedInputStream(is);
		try {
			buildInputStream(bufferedIS);
		} finally {
			try {
				is.close();
				bufferedIS.close();
			} catch (IOException e) {
				final String MSG = "�ر���Դ�ļ�:" + pResource + "ʧ��!";
				logger.warn(MSG, e);
			}
		}
	}

	public void buildProperties(java.util.Properties pProperties) throws ConfigeException {
		properites.clear();
		properites.putAll(pProperties);
	}

	public void buildInputStream(InputStream pIS) throws ConfigeException {
		properites.clear();
		try {
			properites.load(pIS);
		} catch (IOException e) {
			final String MSG = "��ȡ�����ļ�ʧ��!";
			logger.error(MSG, e);
			throw new ConfigeException(MSG, e);
		}
	}

	public ResourceManager buildResourceManager() throws ConfigeException {
		DefaultResourceManager result = new DefaultResourceManager();
		result.setCache(this.createCache());
		result.setLoaderMapping(createLoaderMapping());
		result.setHandlerMapping(createHandlerMapping());
		result.setResourceConfigMapping(this.createResourceConfigMapping());
		String checkedModified = this.properites.getProperty(CHECK_MODIFIED_KEY);
		boolean isChecked = Boolean.valueOf(checkedModified).booleanValue();
		result.setCheckModified(isChecked);
		if (isChecked == false) {
			if (logger.isWarnEnabled()) {
				logger.warn("û�������޸ļ�飬����Դ�ļ��޸ĺ�,�ͻ��˻�ȡ���Ļ���֮ǰ����Ϣ������仯.\n" + "Ҫ�����޸ļ�飬������������:" + CHECK_MODIFIED_KEY
						+ " = true ����.");
			}
		}
		return result;
	}

	private static final String CACHE_KEY = "resource.cache";
	private static final String LOADER_KEY = "resource.loaders";
	private static final String HANDLER_KEY = "resource.handlers";
	private static final String MAPPING_KEY = "resource.uriMappings";
	private static final String CHECK_MODIFIED_KEY = "resouce.checkModified";

	private boolean isEmpty(String pValue) {
		if (pValue == null) {
			return true;
		}
		if ("".equals(pValue.trim())) {
			return true;
		}
		return false;
	}

	private static class ObjectFactory {
		public static Object getObject(String pClass) {
			/**
			 * @TODO: ��������������ô����?
			 */
			try {
				return Class.forName(pClass).newInstance();
			} catch (Exception e) {
				throw new RuntimeException("���������ʧ��!" + pClass, e);
			}
		}
	}

	private static final String CLASS_POSTFIX = "class";

	private void setProprty(Object pObj, String pProperty, String pValue) throws ConfigeException {
		try {
			BeanUtils.setProperty(pObj, pProperty, pValue);
		} catch (Exception ex) {
			final String MSG = "���ö���:" + pObj.getClass().getName() + "��" + pProperty + " ����Ϊ" + pValue + "ʱ�����쳣";
			logger.error(MSG, ex);
			throw new ConfigeException(MSG, ex);
		}
	}

	private Object createObject(String pObjectKeyPrefix, Class pDestClass) throws ConfigeException {
		final String classKey = pObjectKeyPrefix + CLASS_POSTFIX;
		final String className = properites.getProperty(classKey);
		if (className == null) {
			final String MSG = "�����ļ���û�ж������ԣ�" + classKey + ",����ϸ��������ļ���";
			logger.error(MSG);
			throw new ConfigeException(MSG);
		}
		Object obj = ObjectFactory.getObject(className);
		if (pDestClass.isInstance(obj) == false) {
			throw new ConfigeException(className + "δʵ�ֽӿ�:" + pDestClass.getName());
		}
		Map properties = getObjectProperties(pObjectKeyPrefix);
		Iterator propertiesIterator = properties.keySet().iterator();
		while (propertiesIterator.hasNext()) {
			String property = (String) propertiesIterator.next();
			String value = (String) properties.get(property);
			setProprty(obj, property, value);
		}

		return obj;
	}

	/**
	 * key ���������� value������ֵ
	 * 
	 * @param pObjectKeyPrefix
	 * @return
	 */
	private Map getObjectProperties(String pObjectKeyPrefix) {
		Map result = new LinkedHashMap();
		Iterator keyIterator = properites.keySet().iterator();
		final String classKey = pObjectKeyPrefix + CLASS_POSTFIX;
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			boolean isClassKey = key.startsWith(classKey);
			if (isClassKey) {
				continue;
			}
			if (key.startsWith(pObjectKeyPrefix) == false) {
				continue;
			}
			String value = properites.getProperty(key);
			result.put(key.substring(pObjectKeyPrefix.length()), value);
		}
		return result;
	}

	/**
	 * ����cache
	 * 
	 * @return
	 * @throws ConfigeException
	 */
	private Cache createCache() throws ConfigeException {
		String cacheName = properites.getProperty(CACHE_KEY);
		if (isEmpty(cacheName)) {
			logger.info("û�з���cache���ã�����Ĭ�ϵ�MemoryCache");
			return new MemoryCache();
		}
		return (Cache) this.createObject(cacheName + ".", Cache.class);
	}

	/**
	 * ���ַ����������,�ָ����,����;
	 * 
	 * @param pStr
	 * @return
	 */
	private String[] toArray(String pStr) {
		if (pStr == null) {
			return new String[0];
		}
		return StringUtils.tokenizeToStringArray(pStr, Constants.SPLITER);
	}

	/**
	 * ����ResourceLoader����
	 * 
	 * @return
	 * @throws ConfigeException
	 */
	private LoaderMapping createLoaderMapping() throws ConfigeException {
		DefaultLoaderMapping result = new DefaultLoaderMapping();
		String loaderNames = properites.getProperty(LOADER_KEY);
		if (isEmpty(loaderNames)) {
			return result;
		}
		String[] loaderNameArray = toArray(loaderNames);
		for (int i = 0; i < loaderNameArray.length; i++) {
			String loaderName = loaderNameArray[i];
			result.put(loaderName, (ResourceLoader) this.createObject(loaderName + ".", ResourceLoader.class));
		}
		return result;
	}

	private HandlerMapping createHandlerMapping() throws ConfigeException {
		DefaultHandlerMapping result = new DefaultHandlerMapping();
		String handlerNames = properites.getProperty(HANDLER_KEY);
		if (isEmpty(handlerNames)) {
			return result;
		}
		String[] handlerNameArray = toArray(handlerNames);
		for (int i = 0; i < handlerNameArray.length; i++) {
			String handlerName = handlerNameArray[i];
			result.put(handlerName, (ResourceHandler) this.createObject(handlerName + ".", ResourceHandler.class));
		}
		return result;
	}

	private AntPathMatcher uriPatternMatcher = new AntPathMatcher();
	private static final String URI_PATTERN = "*.uriPattern";
	private static final String INCLUDE_PATTERN = "*.includes";

	/**
	 * ��ȡ����uriMapping����
	 * 
	 * @since 1.1
	 * @return
	 */
	private String getUriMappingNames() {
		// return properites.getProperty(MAPPING_KEY);
		/**
		 * Ϊ�˼򻯶���,��1.1��ʼ,resource.uriMappings = js,css���ֶ��岻��Ҫ�� ֱ������ *.uriPattern
		 * ����key,һ��ƥ��,��ȡ���ǰ��ֵ��Ϊ uriMappingName
		 */
		StringBuffer sb = new StringBuffer();
		java.util.Iterator propIterator = properites.keySet().iterator();
		while (propIterator.hasNext()) {
			Object key = propIterator.next();
			if (uriPatternMatcher.match(URI_PATTERN, (String) key)
					|| uriPatternMatcher.match(INCLUDE_PATTERN, (String) key)) {
				String uriMappingName = getUriMappingName((String) key);
				sb.append(uriMappingName).append(Constants.SPLITER);
			}
		}
		return sb.toString();
	}

	private String getUriMappingName(String pKey) {
		int index = pKey.indexOf(".");
		if (index == -1) {
			return null;
		}
		return pKey.substring(0, index);
	}

	private ResourceConfigMapping createResourceConfigMapping() throws ConfigeException {
		DefaultResourceConfigMapping result = new DefaultResourceConfigMapping();
		String uriMappingNames = getUriMappingNames();
		if (isEmpty(uriMappingNames)) {
			return result;
		}
		String[] uriMappingNameArray = toArray(uriMappingNames);
		List uriMappings = new ArrayList();
		for (int i = 0; i < uriMappingNameArray.length; i++) {
			String uriMappingName = uriMappingNameArray[i];
			UriMapping uriMapping = new UriMapping();
			Map properties = getObjectProperties(uriMappingName + ".");
			Iterator propertiesIterator = properties.keySet().iterator();
			while (propertiesIterator.hasNext()) {
				String property = (String) propertiesIterator.next();
				String value = (String) properties.get(property);
				setProprty(uriMapping, property, value);
			}
			uriMappings.add(uriMapping);
		}
		result.setUriMappings((UriMapping[]) uriMappings.toArray(new UriMapping[0]));
		return result;
	}

}