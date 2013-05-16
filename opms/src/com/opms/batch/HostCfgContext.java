package com.opms.batch;

import java.util.HashMap;
import java.util.Map;

import org.eredlab.g4.ccl.util.ResourcesUtil;

import com.common.util.XmlParseUtil;
import com.opms.batch.model.BatchHostCfg;
import com.opms.batch.model.Host;
import com.opms.batch.model.HostCfg;

public class HostCfgContext {

	private static HostCfgContext instance;
	private Map<String, Host> context = new HashMap<String, Host>();
	
	private HostCfgContext() {
		init();
	}
	
	public static HostCfgContext getInstance() {
		if (instance == null) {
			synchronized (HostCfgContext.class) {
				if (instance == null) {
					instance = new HostCfgContext();
				}
			}
		}
		return instance;
	}
	
	public void init() {
		context.clear();
		try {
			HostCfg hostCfg = XmlParseUtil.parseXML(HostCfg.class, ResourcesUtil.getResourceAsFile("host.xml"));
			BatchHostCfg batchHostCfg = hostCfg.getBatchHostCfg();
			if(batchHostCfg != null) {
				for (Host host : batchHostCfg.getHosts()) {
					context.put(host.getId(), host);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Host> getContext() {
		return context;
	}
	
	public Host getHost(String hostId) {
		return context.get(hostId);
	}
	
}
