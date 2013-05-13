package com.opms.batch.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "HostCfg" )
@XmlAccessorType(XmlAccessType.FIELD)
public class HostCfg {

	@XmlElement(name="BatchHostCfg")
	private BatchHostCfg batchHostCfg;

	public BatchHostCfg getBatchHostCfg() {
		return batchHostCfg;
	}

	public void setBatchHostCfg(BatchHostCfg batchHostCfg) {
		this.batchHostCfg = batchHostCfg;
	}
	
}
