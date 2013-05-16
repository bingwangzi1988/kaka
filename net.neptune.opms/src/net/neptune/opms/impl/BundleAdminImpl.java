package net.neptune.opms.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.jws.WebService;

import net.neptune.opms.BundleAdmin;
import net.neptune.opms.model.BundleInfo;
import net.neptune.opms.model.Bundles;
import net.neptune.opms.util.BundleUtil;
import net.neptune.opms.util.OpmsConstants;

import org.eclipse.osgi.framework.internal.core.AbstractBundle;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

@WebService(endpointInterface="net.neptune.opms.BundleAdmin") 
public class BundleAdminImpl implements BundleContextAware, BundleAdmin {

	Logger logger = LoggerFactory.getLogger(BundleAdminImpl.class);  
	private static BundleContext sysBundleContext;
	
	@Override
	public Bundles getBundles() {
		ArrayList<BundleInfo> bundleInfos = new ArrayList<BundleInfo>();
		Bundle[] bundleArr = sysBundleContext.getBundles();
		for(Bundle bundle : bundleArr) {
			bundleInfos.add(BundleUtil.chgBundle(bundle));
		}
		Bundles bundles = new Bundles();
		bundles.setBundleInfos(bundleInfos);
	
		return bundles;
	}
	
	@Override
	public void startBundle(String bundleId) throws Exception {
		Bundle bundle = getBundleById(bundleId);
		bundle.start();
	}
	
	@Override
	public void stopBundle(String bundleId) throws Exception {
		Bundle bundle = getBundleById(bundleId);
		bundle.stop();
	}
	
	@Override
	public void refreshBundle(String bundleId) throws Exception {
		ServiceReference packageAdminRef = sysBundleContext.getServiceReference("org.osgi.service.packageadmin.PackageAdmin"); 
		if (packageAdminRef != null) {
			PackageAdmin packageAdmin = (PackageAdmin) sysBundleContext.getService(packageAdminRef);
			if (packageAdmin != null) {
				try {
					AbstractBundle[] refresh = null;
					Vector<Bundle> bundles = new Vector<Bundle>();
					Bundle bundle = getBundleById(bundleId);
					bundles.addElement(bundle);
					int size = bundles.size();
					refresh = new AbstractBundle[size];
					bundles.copyInto(refresh);
					packageAdmin.refreshPackages(refresh);
				} finally {
					sysBundleContext.ungetService(packageAdminRef);
				}
			}
		} else {
			throw new Exception("CONSOLE_CAN_NOT_REFRESH_NO_PACKAGE_ADMIN_ERROR");
		}
	}
	
	@Override
	public void installBundle(String url) throws Exception {
		sysBundleContext.installBundle(OpmsConstants.BUNDLE_URL_PREFIX + url);
	}
	
	@Override
	public void installAndStartBundle(String url) throws Exception {
		Bundle bundle = (Bundle) sysBundleContext.installBundle(OpmsConstants.BUNDLE_URL_PREFIX + url);
		bundle.start();
	}
	
	@Override
	public void uninstallBundle(String bundleId) throws Exception {
		Bundle bundle = getBundleById(bundleId);
		String location = bundle.getLocation();
		String bundleName = "";
		if(location.lastIndexOf("/") == (location.length()-1)) {
			location = location.substring(0, location.length()-1);
			bundleName = location.substring(location.lastIndexOf("/")+1);
		} else {
			bundleName = location.substring(location.lastIndexOf("/")+1);
		}
		String bundlePath = location.substring(location.lastIndexOf("L000"), location.indexOf("L000")+5);
		logger.error("location: {}", location);
		logger.error("bundleName: {}", bundleName);
		logger.error("bundlePath: {}", bundlePath);
		bundle.uninstall();
		BundleUtil.backupBundle(bundlePath, bundleName);
	}
	
	@Override
	public void updateBundle(String bundleId) throws Exception {
		Bundle bundle = getBundleById(bundleId);
		bundle.update();
	}
	
	@Override
	public void replaceBundle(String bundleId, String url) throws Exception {
		Bundle bundle = getBundleById(bundleId);
		String location = bundle.getLocation();
		logger.error("location: {}", location);
		String bundleName = "";
		if(location.lastIndexOf("/") == (location.length()-1)) {
			location = location.substring(0, location.length()-1);
			bundleName = location.substring(location.lastIndexOf("/")+1);
		} else {
			bundleName = location.substring(location.lastIndexOf("/")+1);
		}
		String bundlePath = url.substring(0, url.lastIndexOf("/"));
		logger.error("bundleName: {}", bundleName);
		logger.error("bundlePath: {}", bundlePath);
		bundle.update(new URL(OpmsConstants.BUNDLE_URL_PREFIX + url).openStream());
		BundleUtil.backupBundle(bundlePath, bundleName);
	}

	public Bundle getBundleById(final String bundleId) throws Exception {
		Bundle bundle = null;
		long id = Long.parseLong(bundleId);
		bundle = sysBundleContext.getBundle(id);
		if(bundle == null) {
			throw new Exception("未找到对应的组件");
		}
		
		return bundle;
	}
	
	@Override
	public void setBundleContext(BundleContext bundleContext) {
		BundleAdminImpl.sysBundleContext = bundleContext;
	}
	
}