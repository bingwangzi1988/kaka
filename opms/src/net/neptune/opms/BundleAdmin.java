package net.neptune.opms;

import javax.jws.WebService;

import net.neptune.opms.model.Bundles;

@WebService
public interface BundleAdmin {

	public Bundles getBundles();

	public void startBundle(final String bundleId) throws Exception;
	
	public void stopBundle(final String bundleId) throws Exception;
	
	public void refreshBundle(final String bundleId) throws Exception;
	
	public void installBundle(final String url) throws Exception;
	
	public void installAndStartBundle(final String url) throws Exception;
	
	public void uninstallBundle(final String bundleId) throws Exception;
	
	public void updateBundle(final String bundleId) throws Exception;
	
	public void replaceBundle(final String bundleId, final String url) throws Exception;
	
}