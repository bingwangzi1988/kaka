package net.neptune.opms;

import javax.xml.ws.Endpoint;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

import net.neptune.opms.impl.BundleAdminImpl;
import net.neptune.opms.impl.FileTransferServiceImpl;
import net.neptune.opms.impl.SysResourceServiceImpl;

public class Activator implements BundleContextAware, CommandProvider {

	private BundleContext context;
	private String hostIp;
	private String hostPort;
	
	public void start() throws Exception {
		context.registerService(CommandProvider.class.getName(), this, null);
	}

	public void _webservice(CommandInterpreter ci) throws Exception{
		Endpoint.publish("http://" + hostIp + ":" + hostPort + "/BundleAdmin", new BundleAdminImpl());
		Endpoint.publish("http://" + hostIp + ":" + hostPort + "/FileTransferService", new FileTransferServiceImpl());
		Endpoint.publish("http://" + hostIp + ":" + hostPort + "/SysResourceService", new SysResourceServiceImpl());
	}
	
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public String getHostPort() {
		return hostPort;
	}
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}

	@Override
	public String getHelp() {
		return "";
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.context = bundleContext;
	}

}
