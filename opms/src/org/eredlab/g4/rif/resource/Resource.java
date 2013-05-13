package org.eredlab.g4.rif.resource;

import java.io.Serializable;

/**
 * Resource
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-11-20
 */
public interface Resource extends Serializable {

	/**
	 * �ַ�������, ��:GBK ,UTF-8
	 * 
	 * @return
	 */
	public String getCharset();

	/**
	 * mimetype, �磺text/html
	 * 
	 * @return
	 */
	public String getMimeType();

	/**
	 * ��Դuri.�������Ϊ��Դ��������Ψһ��ʶ��
	 * 
	 * @return
	 */
	public String getUri();

	/**
	 * ��Դ��2��������,ԭʼ���ݣ�δ���������
	 * 
	 * @return
	 */
	public byte[] getData();

	/**
	 * �Ѿ������������
	 * 
	 * @return
	 */
	public byte[] getTreatedData();

	public void setTreatedData(byte[] pData);

	/**
	 * ��Դ���ϴ��޸�ʱ��
	 * 
	 * @return
	 */
	public long getLastModified();

	public String getResourceCode();

	/**
	 * �Ƿ񾭹�gzipѹ��
	 * 
	 * @return
	 */
	public boolean isGzip();

	public void setGzip(boolean gzip);
}
