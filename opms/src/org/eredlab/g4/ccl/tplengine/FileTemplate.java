package org.eredlab.g4.ccl.tplengine;

/**
 * �ļ�ģ��
 * @author XiongChun
 * @since 2009-07-28
 * @see DefaultTemplate
 */
public class FileTemplate implements DefaultTemplate {
	/**
	 * �ļ�ģ����Դ·��
	 */
	private String resource;
	
	/**
	 * ���캯��
	 * @param pResource �ļ�ģ����Դ·��
	 */
	public FileTemplate(String pResource){
		this.resource = pResource;
	}
    
	/**
	 * ���캯��
	 */
	public FileTemplate() {
	}
    
	/**
	 * ��ȡ�ļ�ģ����Դ·��
	 */
	public String getTemplateResource() {
		return getResource();
	}
	
	/**
	 * �����ļ�ģ����Դ·��
	 */
	public void setTemplateResource(String pResource) {
		this.resource = pResource;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}
