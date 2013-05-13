package org.eredlab.g4.rif.resource.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.Cache;
import org.eredlab.g4.rif.resource.Resource;
import org.eredlab.g4.rif.resource.ResourceException;
import org.eredlab.g4.rif.resource.ResourceHandler;
import org.eredlab.g4.rif.resource.ResourceLoader;
import org.eredlab.g4.rif.resource.ResourceManager;

/**
 * AbstractResourceManager
 * 
 * @author HuangYunHui|XiongChun
 * @since 2010-2-5
 */
public abstract class AbstractResourceManager implements ResourceManager {
	  private final Log logger = LogFactory.getLog( this.getClass() );
	    
	    private Cache cache;
	    private CacheManager cacheManager = null;
	    /**
	     * �Ƿ�����Դ�޸�
	     */
	    private boolean checkModified = true;
	    
	    private Map keyLocks = new HashMap();
	    
	    private ResourceConfigMapping resourceConfigMapping;
	    private LoaderMapping loaderMapping;
	    private HandlerMapping handlerMapping;
	    
	    private final static Object LOCK = new Object();
	    
		/**
		 * ִ����Դ����ĳ�ʼ������
		 * ֻ�ᱻ����һ��
		 * @throws ResourceException
		 */
		public void init() throws ResourceException{
			if ( cache != null ){
			   cache.init();
			}
		}
		
		  /**
	     * ִ���������
		 * ֻ�ᱻ����һ�� 
	     * @throws ResourceException
	     */
	    public void destroy() throws ResourceException{
	      if (cache != null ) {
	    	  cache.destroy();	
	      }
	    }
	    
	    
	    /**
	     * @param pUri
	     * @return
	     */
	    private Object getKeyLock(String pUri){
	    	Object result = keyLocks.get(pUri);
	    	if ( result == null ){
	    		result = new Object();
	    		keyLocks.put(pUri, result);
	    	}
	    	return result;
	    }
		public Resource get(String pUri) throws ResourceException {
			ResourceConfig resourceConfig = resourceConfigMapping.mapping(pUri);
			boolean isCache = false;
			if ( resourceConfig == null ){
				final String msg =
					"û���ҵ���Դ: " + pUri + " ��Ӧ��������Ŀ�����������ļ����Ƿ������֮ƥ���uriģʽ!";
				logger.debug(msg);
				return null;
			}
			
			
			
			ResourceLoader loader = null;
			isCache = resourceConfig.isCache();
			loader = loaderMapping.mapping(resourceConfig.getLoaderName());
	
			Resource result = cacheManager.get(pUri);
		
			if ( result != null ){	
				if ( checkModified == false ){//������޸�
					return result;
				}
				//�����Դ�Ƿ����޸�
				long lastModified = result.getLastModified();
				long newModified = loader.getLastModified(pUri);
				if ( newModified > lastModified){
					Object keyLock = null;
					synchronized( LOCK ){
						keyLock = getKeyLock(pUri); 
					}
					synchronized(keyLock){
						result = loadResource(loader, pUri);
						cacheManager.put(result);
					}

				}
				return result;
			}
		
			Object keyLock = null;
			synchronized( LOCK ){
				keyLock = getKeyLock(pUri); 
			}
			synchronized(keyLock){
				result = cacheManager.get(pUri);
				if ( result != null ){
					return result;
				}
				result = loadResource(loader, pUri);
				//���cache ����Ҫ����Դ��������
				if ( isCache ){
					cacheManager.put(result);	
				}
				
				return result;
			}
		}

		/**
		 * ����װ����Դ������hanlder������Դ����
		 * ע�⣺����Դ���д���Ĺ����У������һ��������̳����ˣ�������������
		 * ������ԭʼ�ĵ��������.
		 * @param pLoader
		 * @param pUri
		 * @return
		 */
		private Resource loadResource(ResourceLoader pLoader , String pUri){
			Resource res = null;
			ResourceConfig resourceConfig = resourceConfigMapping.mapping(pUri);
			if ( resourceConfig == null ){
				res = pLoader.load(pUri);
				return res;
			} else {
				res = pLoader.load( 
						newURI( pUri, 
								resourceConfig.getOldPrefix(), 
								resourceConfig.getNewPrefix()) );
			}
			String[] handlers = resourceConfig.getHandlerNames();

			for(int i=0; i<handlers.length; i++){
				String handerName = handlers[i];
				ResourceHandler handler = handlerMapping.mapping(handerName);
				if ( handler == null ){
					logger.warn("û���ҵ���Ϊ:" + handerName + "����Դ������");
					continue;
				}
				try{				
				    handler.handle(res);
				}catch(Exception ex){
					logger.warn("����Դ:" + pUri + "����:" + handerName + " ����ʱ�����쳣!", ex );
					continue;
				}
			}
			return res;
		}
		
		private static String newURI(String pUri, String pOldPrefix, String pNewPrefix ){
			if ( pUri == null ){
				return null;
			}
			if ( pOldPrefix == null || pNewPrefix == null ){
				return pUri;
			}
			if ( pUri.startsWith(pOldPrefix) ){
				return pNewPrefix + pUri.substring(pOldPrefix.length());
			} else {
				return pUri;
			}
		}
 
		public Cache getCache() {
			return cache;
		}
		public void setCache(Cache cache) {
			this.cache = cache;
			this.cacheManager = new CacheManager(cache);
		}
		public HandlerMapping getHandlerMapping() {
			return handlerMapping;
		}
		public void setHandlerMapping(HandlerMapping handlerMapping) {
			this.handlerMapping = handlerMapping;
		}
		public LoaderMapping getLoaderMapping() {
			return loaderMapping;
		}
		public void setLoaderMapping(LoaderMapping loaderMapping) {
			this.loaderMapping = loaderMapping;
		}
		public ResourceConfigMapping getResourceConfigMapping() {
			return resourceConfigMapping;
		}
		public void setResourceConfigMapping(ResourceConfigMapping resourceConfigMapping) {
			this.resourceConfigMapping = resourceConfigMapping;
		}

		public boolean isCheckModified() {
			return checkModified;
		}

		public void setCheckModified(boolean checkModified) {
			this.checkModified = checkModified;
		}

}
