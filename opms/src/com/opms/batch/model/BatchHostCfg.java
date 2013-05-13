package com.opms.batch.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "BatchHostCfg" )
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchHostCfg {

	@XmlElement(name="Host")
	private List<Host> hosts;

	public List<Host> getHosts() {
		return hosts;
	}

	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}
	
}
