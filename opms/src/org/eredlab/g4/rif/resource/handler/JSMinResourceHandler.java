package org.eredlab.g4.rif.resource.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.HandleResourceException;
import org.eredlab.g4.rif.resource.Resource;
import org.eredlab.g4.rif.resource.ResourceHandler;

/**
 * JSMinResourceHandler
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-08-10
 */
public class JSMinResourceHandler implements ResourceHandler {

	private final Log logger = LogFactory.getLog(this.getClass());

	public void handle(Resource pResource) throws HandleResourceException {
		InputStream is = new ByteArrayInputStream(pResource.getTreatedData());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSMin jsMin = new JSMin(is, out);
		logger.info("���ڶ���Դ:" + pResource.getUri() + "����js minѹ��...");
		try {
			jsMin.jsmin();
			pResource.setTreatedData(out.toByteArray());
			logger.info("js min��Դ:" + pResource.getUri() + "�ɹ�.");
		} catch (Exception ex) {
			final String MSG = "js min��Դ:" + pResource.getUri() + "ʧ�ܣ�";
			logger.warn(MSG, ex);
			return;
		}

	}

}
