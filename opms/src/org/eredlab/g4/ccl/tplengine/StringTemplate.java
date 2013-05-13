package org.eredlab.g4.ccl.tplengine;

/**
 * �ַ���ģ��
 * @author XiongChun
 * @since 2009-07-28
 * @see DefaultTemplate
 */
public class StringTemplate implements DefaultTemplate {
	/**
	 * �ַ���ģ������
	 */
	private String resource;
	
	/**
	 * ���캯��
	 * @param pResource �ַ���ģ������
	 */
	public StringTemplate(String pResource){
		this.resource = pResource;
	}
	
	/**
	 * ȱʡ���캯��
	 */
	public StringTemplate() {
	}

	/**
	 * ��ȡ�ַ���ģ������
	 */
	public String getTemplateResource() {
		return getResource();
	}

	/**
	 * �����ַ���ģ������
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
