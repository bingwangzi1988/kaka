package org.eredlab.g4.rif.resource;

/**
 * ResourceManager
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-11-20
 */
public interface ResourceManager {

	/**
	 * ִ����Դ����ĳ�ʼ������ ֻ�ᱻ����һ��
	 * 
	 * @throws ResourceException
	 */
	public void init() throws ResourceException;

	/**
	 * ��ȡ��Դ
	 * 
	 * @param pUri
	 * @return
	 * @throws ResourceException
	 */
	public Resource get(final String pUri) throws ResourceException;

	/**
	 * ִ��������� ֻ�ᱻ����һ��
	 * 
	 * @throws ResourceException
	 */
	public void destroy() throws ResourceException;

}
