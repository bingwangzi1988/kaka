package org.eredlab.g4.ccl.tplengine;

/**
 * ģ����������
 * @author XiongChun
 * @since 2009-07-26
 */
public class TemplateType {
	
	/**
	 * ��������
	 */
	private String type;
	
	/**
	 * ��������
	 */
	private String description;
	
	/**
	 * Velocity���涨��
	 */
	public static final TemplateType VELOCITY = new TemplateType("Velocity", "Velocity engine");

	/**
	 * ���캯��
	 * @param pType ��������
	 * @param pDescription ��������
	 */
	public TemplateType(String pType, String pDescription) {
		this.type = pType;
		this.description = pDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
