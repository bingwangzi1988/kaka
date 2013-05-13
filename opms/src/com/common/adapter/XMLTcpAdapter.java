package com.common.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eredlab.g4.bmf.aop.SpringBeanAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLTcpAdapter extends Adapter {

	private static Logger log = LoggerFactory.getLogger(XMLTcpAdapter.class);

	@SuppressWarnings("null")
	@Override
	public Object doComm(Object request) throws Exception {

		log.debug("ͨѶ�ӳ�����ʼ.......");

		Socket client = null;
		byte[] buff = (byte[]) request; // ͨѶ����
		OutputStream os = null;
		InputStream is = null;
		byte[] content = null;
		try {
			client = new Socket(this.ip, this.port);
			os = client.getOutputStream();
			is = client.getInputStream();
			client.setSoTimeout(timeout);

			os.write(buff);

			byte[] b = new byte[10];
			int len = is.read(b);
			byte[] body = new byte[Integer.parseInt(new String(b))];
			int bodyLen = is.read(body);

			content = new byte[bodyLen];
			System.arraycopy(body, 0, content, 0, bodyLen);
			os.close();
			is.close();

			log.debug("ͨѶ�ӳ��������,���ص�����.......[{}]", new String(content, "UTF-8"));

		} catch (UnknownHostException e) {
			log.error("�޷����������µ�ַ������{}", ip+":"+port, e);
			throw e;
		} catch (IOException e) {
			log.error("ͨѶ�쳣 {}:{}", ip, port);
			throw e;
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
				if (client != null)
					client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				throw e1;
			}
			// try {
			// client.close();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		}
		return content;
	}

}
