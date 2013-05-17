package net.neptune.opms.impl;

import java.util.ArrayList;

import javax.jws.WebService;

import net.neptune.opms.SysResourceService;
import net.neptune.opms.model.CPUInfo;
import net.neptune.opms.model.MEMInfo;
import net.neptune.opms.model.OSInfo;
import net.neptune.opms.util.SysResourceUtil;

@WebService(endpointInterface="net.neptune.opms.SysResourceService") 
public class SysResourceServiceImpl implements SysResourceService {

	@Override
	public OSInfo getOSInf() {
		return SysResourceUtil.getOSInf();
	}

	@Override
	public MEMInfo getMemInfo() {
		return SysResourceUtil.getMemInfo();
	}

	@Override
	public ArrayList<CPUInfo> getCpuInfo() {
		return SysResourceUtil.getCpuInfo();
	}

}