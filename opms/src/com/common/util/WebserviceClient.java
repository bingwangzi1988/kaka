package com.common.util;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * WebService Client
 * 
 * @author bingwangzi
 *
 */
public class WebserviceClient {

	/**
	 * 获取指定WebService服务
	 * 
	 * @param ip
	 * @param port
	 * @param serviceName
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getServiceClient(final String ip, final String port, final String serviceName, Class<E> clazz) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();   
		factory.setServiceClass(clazz);   
		StringBuffer addressBuf = new StringBuffer();
		addressBuf.append("http://").append(ip).append(":").append(port).append("/").append(serviceName);
		factory.setAddress(addressBuf.toString());        
		return (E) factory.create();   
	}
	
}
