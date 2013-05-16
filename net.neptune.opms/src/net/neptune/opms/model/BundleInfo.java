package net.neptune.opms.model;

public class BundleInfo {

	private long id;
	private String name;
	private String version;
	private String state;
	private String lastModified;
	private String manifestHeaders;
	private String type;
	private String path;
	private String startFlag;
	private String stopFlag;
	private String refreshFlag;
	private String updateFlag;
	private String uninstallFlag;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	public String getManifestHeaders() {
		return manifestHeaders;
	}
	public void setManifestHeaders(String manifestHeaders) {
		this.manifestHeaders = manifestHeaders;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStartFlag() {
		return startFlag;
	}
	public void setStartFlag(String startFlag) {
		this.startFlag = startFlag;
	}
	public String getStopFlag() {
		return stopFlag;
	}
	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}
	public String getRefreshFlag() {
		return refreshFlag;
	}
	public void setRefreshFlag(String refreshFlag) {
		this.refreshFlag = refreshFlag;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getUninstallFlag() {
		return uninstallFlag;
	}
	public void setUninstallFlag(String uninstallFlag) {
		this.uninstallFlag = uninstallFlag;
	}
	
}
