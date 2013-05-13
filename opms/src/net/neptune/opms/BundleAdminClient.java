package net.neptune.opms;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class BundleAdminClient {

	public static BundleAdmin getBundleAdmin(final String ip, final String port) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();   
		//注册WebService接口   
		factory.setServiceClass(BundleAdmin.class);   
		//设置WebService地址   
		StringBuffer addressBuf = new StringBuffer();
		addressBuf.append("http://").append(ip).append(":").append(port).append("/BundleAdmin");
		factory.setAddress(addressBuf.toString());        
		BundleAdmin userServices = (BundleAdmin)factory.create();   
		
		return userServices;
	}
	
}
