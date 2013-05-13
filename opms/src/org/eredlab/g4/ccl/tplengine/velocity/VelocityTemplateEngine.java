package org.eredlab.g4.ccl.tplengine.velocity;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.tplengine.AbstractTemplateEngine;
import org.eredlab.g4.ccl.tplengine.DefaultTemplate;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * Velocityģ������
 * @author XiongChun
 * @since 2009-07-28
 * @see com.eredlab.eredbos.eredccp.templateengine.AbstractTemplateEngine
 */
public class VelocityTemplateEngine extends AbstractTemplateEngine {
	
	Log log = LogFactory.getLog(VelocityTemplateEngine.class);
	
	/**
	 * �����ļ�ģ��
	 * @param pTemplate ģ�����
	 * @param pDto �ϲ���������(��ģ�����������ȫ��ѹ��Dto)
	 * @return ����StringWriter����
	 * @throws Exception 
	 */
	protected StringWriter mergeStringTemplate(DefaultTemplate pTemplate, Dto pDto) {
		VelocityEngine ve = VelocityHelper.getVelocityEngine();
		String strTemplate = pTemplate.getTemplateResource();
		if(G4Utils.isEmpty(strTemplate)){
			throw new IllegalArgumentException(GlobalConstants.Exception_Head + "�ַ���ģ�岻��Ϊ��");
		}
		StringWriter writer = new StringWriter();
		VelocityContext context = VelocityHelper.convertDto2VelocityContext(pDto);
		try {
			if(log.isDebugEnabled())
				log.debug("�ַ���ģ��Ϊ:\n" + strTemplate);
				log.debug("eRedģ����������,���������ַ���ģ��ϲ�...");
			ve.evaluate(context, writer, "eRedTemplateEngine.log", strTemplate);
			if(log.isDebugEnabled())
				log.debug("�ַ���ģ��ϲ��ɹ�.�ϲ��������:\n" + writer);
		} catch (Exception e) {
			log.error(GlobalConstants.Exception_Head + "�ַ���ģ��ϲ�ʧ��");
			e.printStackTrace();
		}
		return writer;
	}
	
	/**
	 * �����ַ���ģ��
	 * @param pTemplate ģ�����
	 * @param pDto �ϲ���������(��ģ�����������ȫ��ѹ��Dto)
	 * @return ����StringWriter����
	 * @throws Exception 
	 * @throws Exception 
	 */
	protected StringWriter mergeFileTemplate(DefaultTemplate pTemplate, Dto pDto) {
		VelocityEngine ve = VelocityHelper.getVelocityEngine();
		String filePath = pTemplate.getTemplateResource();
		if(G4Utils.isEmpty(filePath)){
			throw new IllegalArgumentException(GlobalConstants.Exception_Head + "�ļ�ģ����Դ·������Ϊ��");
		}
		StringWriter writer = new StringWriter();
		Template template = null;
		try {
			if(log.isDebugEnabled())
				log.debug("eRedģ����������,���������ļ�ģ��...");
			template = ve.getTemplate(filePath);
			if(log.isDebugEnabled())
				log.debug("�����ļ�ģ��ɹ�");
		} catch (Exception e) {
			log.error(GlobalConstants.Exception_Head + "�����ļ�ģ��ʧ��");
			e.printStackTrace();
		}
		VelocityContext context = VelocityHelper.convertDto2VelocityContext(pDto);
		try {
			if(log.isDebugEnabled())
				log.debug("eRedģ����������,���������ļ�ģ��ϲ�...");
			template.merge(context, writer);
			if(log.isDebugEnabled())
				log.debug("�ϲ��ļ�ģ��ɹ�.�ϲ��������:\n" + writer);
		} catch (Exception e) {
			if(log.isDebugEnabled())log.error(GlobalConstants.Exception_Head + "�ļ�ģ��ϲ�ʧ��");
			e.printStackTrace();
		} 
		return writer;
	}
	
}
