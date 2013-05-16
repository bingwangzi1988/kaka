package net.neptune.opms.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;

import net.neptune.opms.model.BundleInfo;

import org.osgi.framework.Bundle;

public class BundleUtil {

	public static BundleInfo chgBundle(Bundle bundle) {
		BundleInfo bundleInfo = new BundleInfo();
		bundleInfo.setId(bundle.getBundleId());
		bundleInfo.setName(bundle.getSymbolicName());
		bundleInfo.setState(chgBundleState(bundle.getState()));
		bundleInfo.setVersion(bundle.getVersion().toString());
		bundleInfo.setLastModified(chgBundleLastModified(bundle.getLastModified()));
		bundleInfo.setManifestHeaders(chgBundleManifestHeaders(bundle.getHeaders()));
		bundleInfo.setStartFlag("1");
		bundleInfo.setStopFlag("1");
		bundleInfo.setRefreshFlag("1");
		
		return bundleInfo;
	}
	
	public static void backupBundle(final String bundlePath, final String bundleName) throws Exception {
		String srcfilePath = OpmsConstants.PROJECT_BUNDLE_PATH + bundlePath + "/" + bundleName;
		String destDir = OpmsConstants.PROJECT_BACKUP_PATH + DateUtil.formatDateToStr(new Date(), "yyyyMMdd");
		String destFilePath = destDir + "/" + bundleName;
		File destFile = new File(destFilePath);
		if(destFile.exists()) {
			destFile.delete();
		}
		FileToolKit.moveFile(srcfilePath, destDir);
	}
	
	private static String chgBundleState(int stateValue) {
		String state = "";
		
		switch (stateValue) {
		case Bundle.ACTIVE:
			state = "ACTIVE";
			break;
		case Bundle.INSTALLED:
			state = "INSTALLED";
			break;
		case Bundle.RESOLVED:
			state = "RESOLVED";
			break;
		case Bundle.STARTING:
			state = "STARTING";
			break;
		case Bundle.STOPPING:
			state = "STOPPING";
			break;
		case Bundle.UNINSTALLED:
			state = "UNINSTALLED";
			break;
		default:
			break;
		}
		
		return state;
	}
	
	private static String chgBundleLastModified(long lastModified) {
		Date date = new Date(lastModified);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	private static String chgBundleManifestHeaders(Dictionary dictionary) {
		Enumeration enumeration = dictionary.keys();
		StringBuffer headerBuf = new StringBuffer();
		while(enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			headerBuf.append("<span style=color:blue;>")
					.append(key)
					.append(": ")
					.append("</span>")
					.append(dictionary.get(key))
					.append("<br />");
		}
		
		return headerBuf.toString();
	}
	
}
