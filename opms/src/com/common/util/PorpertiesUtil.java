package com.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.util.PropertiesHelper;


public class PorpertiesUtil {
	
	public static Properties getProperties(String propsName){
		Properties props = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	   	if (classLoader == null) {
	   		classLoader = PropertiesHelper.class.getClassLoader();
		}
	   	InputStream is = null;
		try {
			is = classLoader.getResourceAsStream(propsName);
			props.load(is);
		}catch(Exception e1) {
			e1.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
		
	}
}
