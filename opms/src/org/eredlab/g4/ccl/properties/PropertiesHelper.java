package org.eredlab.g4.ccl.properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * Properties������
 * @author XiongChun
 * @since 2009-07-10
 */
public class PropertiesHelper {
	private static Log log = LogFactory.getLog(PropertiesHelper.class);
	private static String filePath;
	private Properties objProperties;
	
	/**
	 * ���캯��
	 * @param is �����ļ�������
	 * @throws Exception
	 */
	public PropertiesHelper(InputStream is) throws Exception {
		try{
			objProperties = new Properties();
			objProperties.load(is);
		}
		catch(FileNotFoundException e){
			log.error(GlobalConstants.Exception_Head + "δ�ҵ�������Դ�ļ�!");
			e.printStackTrace();
			throw e;
		}
		catch(Exception e){
			log.error(GlobalConstants.Exception_Head + "��ȡ������Դ�ļ�����δ֪����!");
			e.printStackTrace();
			throw e;
		}finally{
			is.close();
		}
	}

    /**
     * �־û������ļ�<br>
     * ʹ��setProperty()�������Ժ�,������ô˷������ܽ����Գ־û��������ļ���
     * @param pFileName �����ļ���
     * @throws IOException 
     */
	public void storefile(String pFileName){
		FileOutputStream outStream = null;
		try{
			File file = new File(pFileName + ".properties");
			outStream = new FileOutputStream(file);
			objProperties.store(outStream, "#eRedG4");
		}catch(Exception e){
			log.error(GlobalConstants.Exception_Head + "���������ļ�����.");
			e.printStackTrace();
		}finally{
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

    /**
     * ��ȡ����ֵ
     * @param key ָ��Keyֵ����ȡvalue
     * @return String ��������ֵ
     */
	public String getValue(String key){
		return objProperties.getProperty(key);
	}

    /**
     * ��ȡ����ֵ,֧��ȱʡ����
     * @param key
     * @param defaultValue ȱʡֵ
     * @return String ��������ֵ
     */
	public String getValue(String key, String defaultValue){
		return objProperties.getProperty(key, defaultValue);
	}

    /**
     * ɾ������
     * @param key ����Key
     */
	public void removeProperty(String key){
		objProperties.remove(key);
	}
	
    /**
     * ��������
     * @param key ����Key
     * @param value ����ֵ
     */
	public void setProperty(String key, String value){
		objProperties.setProperty(key, value);
	}
	
    /**
     * ��ӡ��������ֵ
     */
	public void printAllVlue(){
		 objProperties.list(System.out);
	}
}
