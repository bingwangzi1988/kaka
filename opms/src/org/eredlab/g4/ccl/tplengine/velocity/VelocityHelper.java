package org.eredlab.g4.ccl.tplengine.velocity;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * Velocityģ�����渨����
 * @author XiongChun
 * @since 2009-07-28
 */
public class VelocityHelper {
	
	private static Log log = LogFactory.getLog(VelocityHelper.class);
	
	/**
	 * ʵ����Velocityģ�����沢����<br>
	 * <b>˵��:</b>Ϊ�˱���VelocityEngine��������Ŀ�õ���VelocityEngine������ͻ,��������ʹ�ö�ʵ������,
	 * �����ǵ�һʵ��(singleton)����.
	 * @return ����VelocityEngineʵ��
	 * @throws InitVelocityEngineException 
	 */
	public static VelocityEngine getVelocityEngine() throws InitVelocityEngineException{
		VelocityEngine ve = new VelocityEngine();
		try {
			ve.init(getDefaultProperties());
		} catch (Exception e) {
			throw new InitVelocityEngineException(e.getMessage());
		}
		return ve;
	}
	
	/**
	 * ����Velocityģ���������������ļ�
	 * @return
	 */
	public static Properties getDefaultProperties() {
	   	InputStream is = VelocityHelper.class.getResourceAsStream("velocity.properties");
	   	Properties props = new Properties();
		try {
			props.load(is);
			is.close();
		} catch (Exception e) {
		    log.error(GlobalConstants.Exception_Head + "����Velocityģ���������������ļ�����");
		    log.error(e.getMessage());
		    }
			return props;
		}
	
	/**
	 * ��Dto����ת��ΪVelocityContext����
	 * @param pDto �����Dto����
	 * @return ����VelocityContext����
	 */
	public static VelocityContext convertDto2VelocityContext(Dto pDto){
		if(G4Utils.isEmpty(pDto))
			return null;
		Iterator it = pDto.keySet().iterator();
		VelocityContext context = new VelocityContext();
		while(it.hasNext()){
			String key = (String)it.next();
			Object value = pDto.get(key);
			context.put(key, value);
		}
		return context;
	}
}
