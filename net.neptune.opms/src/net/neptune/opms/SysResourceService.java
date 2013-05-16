package net.neptune.opms;

import java.util.ArrayList;

import javax.jws.WebService;

import net.neptune.opms.model.CPUInfo;
import net.neptune.opms.model.MEMInfo;
import net.neptune.opms.model.OSInfo;

@WebService
public interface SysResourceService {

	public OSInfo getOSInf();
	
	public MEMInfo getMemInfo();
	
	public ArrayList<CPUInfo> getCpuInfo();
	
}