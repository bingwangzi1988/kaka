package com.opms.batch.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "Host" )
@XmlAccessorType(XmlAccessType.FIELD)
public class Host {

	@XmlAttribute(name="id")
	private String id;
	@XmlAttribute(name="ip")
	private String ip;
	@XmlAttribute(name="port")
	private String port;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
}
