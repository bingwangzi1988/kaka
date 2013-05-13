package com.common.util;

public class SystemUtil {

	public static String getOpSystemType() {
		String opSysType = "LINUX";
		
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			opSysType = "WINDOWS";
		}
		
		return opSysType;
	}
	
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}
	
}
