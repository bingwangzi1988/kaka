package org.eredlab.g4.ccl.tplengine;

import java.io.StringWriter;

import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ģ������ӿ�
 * @author XiongChun
 * @since 2009-07-26
 */
public interface TemplateEngine {
	/**
	 * ����ģ��
	 * @param pTemplate ģ�����
	 * @param pDto �ϲ���������(��ģ�����������ȫ��ѹ��Dto)
	 * @return �����������StringWriter����
	 */
	public StringWriter mergeTemplate(DefaultTemplate pTemplate, Dto pDto);
	
}
