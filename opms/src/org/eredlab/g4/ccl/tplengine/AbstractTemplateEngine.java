package org.eredlab.g4.ccl.tplengine;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * ģ������������
 * @author XiongChun
 * @since 2009-07-28
 * @see TemplateEngine
 */
public abstract class AbstractTemplateEngine implements TemplateEngine {
	
	private Log log = LogFactory.getLog(AbstractTemplateEngine.class);
	
	/**
	 * ����ģ��
	 * @param pTemplate ģ�����
	 * @param pDto �ϲ���������(��ģ�����������ȫ��ѹ��Dto)
	 * @return writer�����������StringWriter����
	 */
	public StringWriter mergeTemplate(DefaultTemplate pTemplate, Dto dto) {
		StringWriter writer = null;
		if(pTemplate instanceof StringTemplate){
			writer = mergeStringTemplate(pTemplate, dto);
		}else if(pTemplate instanceof FileTemplate){
			writer = mergeFileTemplate(pTemplate, dto);
		}else{
			throw new IllegalArgumentException(GlobalConstants.Exception_Head + "��֧�ֵ�ģ��" );
		}
		return writer;
	}
	
	/**
	 * �����ַ���ģ��
	 * @param pTemplate ģ�����
	 * @return ����StringWriter����
	 * @param pDto �ϲ���������(��ģ�����������ȫ��ѹ��Dto)
	 * @throws Exception 
	 */
	protected abstract StringWriter mergeStringTemplate(DefaultTemplate pTemplate, Dto pDto);
	
	/**
	 * �����ļ�ģ��
	 * @param pTemplate ģ�����
	 * @param pDto �ϲ���������(��ģ�����������ȫ��ѹ��Dto)
	 * @return ����StringWriter����
	 * @throws Exception 
	 */
	protected abstract StringWriter mergeFileTemplate(DefaultTemplate pTemplate, Dto pDto);

}
