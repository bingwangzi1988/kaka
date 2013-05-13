package org.eredlab.g4.rif.resource;

/**
 * ResourceLoader
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-11-20
 */
public interface ResourceLoader {

	/**
	 * ��ȡ��Դ�ϴ��޸�ʱ��
	 * 
	 * @param pUri
	 * @return
	 */
	public long getLastModified(String pUri);

	/**
	 * @param pUri
	 *            ��Դ��ʶ��
	 * @return ��Դ����
	 * @throws Exception
	 *             װ����Դʧ��
	 */
	public Resource load(final String pUri) throws LoadResoruceException;
}
