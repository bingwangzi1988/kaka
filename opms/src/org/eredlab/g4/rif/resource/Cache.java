package org.eredlab.g4.rif.resource;

/**
 * Cache
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-11-01
 */
public interface Cache {

	/**
	 * ��ʼ��cache
	 * 
	 * @throws CacheException
	 */
	public void init() throws CacheException;

	/**
	 * �������ݵ�cache
	 * 
	 * @param key
	 * @param pValue
	 * @throws CacheException
	 */
	public void put(Object key, Object pValue) throws CacheException;

	/**
	 * ��cache�л�ȡ���ݣ���cache�в�����ָ��keyʱ������null
	 * 
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	public Object get(Object key) throws CacheException;

	/**
	 * ��cache��ɾ������
	 * 
	 * @param key
	 * @throws CacheException
	 */
	public void remove(Object key) throws CacheException;

	/**
	 * ���cache
	 * 
	 * @throws CacheException
	 */
	public void clear() throws CacheException;

	/**
	 * �����init���˳�ʼ���������Ϳ�����Ҫ�ڽ���clean����
	 * 
	 * @throws CacheException
	 */
	public void destroy() throws CacheException;
}
