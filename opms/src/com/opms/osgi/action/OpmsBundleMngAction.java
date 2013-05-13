package com.opms.osgi.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.neptune.opms.BundleAdminClient;
import net.neptune.opms.FileTransferClient;
import net.neptune.opms.model.BundleInfo;
import net.neptune.opms.model.Bundles;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.base.BaseAction;
import com.common.util.Utils;
import com.opms.osgi.po.OpmsBundleCfg;
import com.opms.osgi.service.OpmsBundleCfgService;

public class OpmsBundleMngAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(OpmsBundleMngAction.class);

	OpmsBundleCfgService  opmsBundleCfgService = (OpmsBundleCfgService) SpringBeanLoader.getSpringBean("opmsBundleCfgService");
	
	private JSON resultJson;
	private File upload;
	private String uploadFileName;
	
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String hostIp = request.getParameter("hostIp");
		String hostPort = request.getParameter("hostPort");
		String systemId = request.getParameter("systemId");
		String bundleName = request.getParameter("bundleName");
		String bundleType = request.getParameter("bundleType");
		String bundleState = request.getParameter("bundleState");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
	
		int startInt = 0;
		int limitInt = 0;
		if (start != null && start.trim().length() > 0) {
			startInt = Integer.parseInt(start);
		}
		if (limit != null && limit.trim().length() > 0) {
			limitInt = Integer.parseInt(limit);
		}

		List<BundleInfo> dataList = new ArrayList<BundleInfo>();
		ArrayList<BundleInfo> tempList = new ArrayList<BundleInfo>();
		Bundles bundles = null;
		int count = 0;
		try {
			bundles = BundleAdminClient.getBundleAdmin(hostIp, hostPort).getBundles();
			ArrayList<BundleInfo> bundleList = bundles.getBundleInfos();
			
			for (BundleInfo bundleInfo : bundleList) {
				if (bundleName != null && bundleName.trim().length() > 0) {
					if (bundleInfo.getName().contains(bundleName)) {
						OpmsBundleCfg opmsBundleCfg = opmsBundleCfgService.searchOpmsBundleCfg(systemId, bundleInfo.getName());
						if (bundleType != null && bundleType.trim().length() > 0) {
							if(opmsBundleCfg != null) {
								if(bundleType.equals(opmsBundleCfg.getBundleType())) {
									bundleInfo.setType(opmsBundleCfg.getBundleType());
									bundleInfo.setPath(opmsBundleCfg.getBundlePath());
									bundleInfo.setStartFlag(opmsBundleCfg.getStartFlag());
									bundleInfo.setStopFlag(opmsBundleCfg.getStopFlag());
									bundleInfo.setRefreshFlag(opmsBundleCfg.getRefreshFlag());
									bundleInfo.setUpdateFlag(opmsBundleCfg.getUpdateFlag());
									bundleInfo.setUninstallFlag(opmsBundleCfg.getUninstallFlag());
									if (bundleState != null && bundleState.trim().length() > 0) {
										if(bundleState.equals(bundleInfo.getState())) {
											tempList.add(bundleInfo);
										}
									} else {
										tempList.add(bundleInfo);
									}
								}
							} else if("4".equals(bundleType)) {
								bundleInfo.setStartFlag("0");
								bundleInfo.setStopFlag("0");
								bundleInfo.setRefreshFlag("0");
								bundleInfo.setUpdateFlag("0");
								bundleInfo.setUninstallFlag("0");
								if (bundleState != null && bundleState.trim().length() > 0) {
									if(bundleState.equals(bundleInfo.getState())) {
										tempList.add(bundleInfo);
									}
								} else {
									tempList.add(bundleInfo);
								}
							}
						} else {
							bundleInfo.setStartFlag("0");
							bundleInfo.setStopFlag("0");
							bundleInfo.setRefreshFlag("0");
							bundleInfo.setUpdateFlag("0");
							bundleInfo.setUninstallFlag("0");
							if (bundleState != null && bundleState.trim().length() > 0) {
								if(bundleState.equals(bundleInfo.getState())) {
									tempList.add(bundleInfo);
								}
							} else {
								tempList.add(bundleInfo);
							}
						}
					}
				} else {
					OpmsBundleCfg opmsBundleCfg = opmsBundleCfgService.searchOpmsBundleCfg(systemId, bundleInfo.getName());
					if (bundleType != null && bundleType.trim().length() > 0) {
						if(opmsBundleCfg != null) {
							if(bundleType.equals(opmsBundleCfg.getBundleType())) {
								bundleInfo.setType(opmsBundleCfg.getBundleType());
								bundleInfo.setPath(opmsBundleCfg.getBundlePath());
								bundleInfo.setStartFlag(opmsBundleCfg.getStartFlag());
								bundleInfo.setStopFlag(opmsBundleCfg.getStopFlag());
								bundleInfo.setRefreshFlag(opmsBundleCfg.getRefreshFlag());
								bundleInfo.setUpdateFlag(opmsBundleCfg.getUpdateFlag());
								bundleInfo.setUninstallFlag(opmsBundleCfg.getUninstallFlag());
								if (bundleState != null && bundleState.trim().length() > 0) {
									if(bundleState.equals(bundleInfo.getState())) {
										tempList.add(bundleInfo);
									}
								} else {
									tempList.add(bundleInfo);
								}
							}
						} else if("4".equals(bundleType)) {
							bundleInfo.setStartFlag("0");
							bundleInfo.setStopFlag("0");
							bundleInfo.setRefreshFlag("0");
							bundleInfo.setUpdateFlag("0");
							bundleInfo.setUninstallFlag("0");
							if (bundleState != null && bundleState.trim().length() > 0) {
								if(bundleState.equals(bundleInfo.getState())) {
									tempList.add(bundleInfo);
								}
							} else {
								tempList.add(bundleInfo);
							}
						}
					} else {
						bundleInfo.setStartFlag("0");
						bundleInfo.setStopFlag("0");
						bundleInfo.setRefreshFlag("0");
						bundleInfo.setUpdateFlag("0");
						bundleInfo.setUninstallFlag("0");
						if (bundleState != null && bundleState.trim().length() > 0) {
							if(bundleState.equals(bundleInfo.getState())) {
								tempList.add(bundleInfo);
							}
						} else {
							tempList.add(bundleInfo);
						}
					}
				}
			}

			if (tempList != null && tempList.size() > 0) {
				count = tempList.size();
				int countNum = 0;
				if (tempList.size() % limitInt == 0) {
					countNum = startInt + limitInt;
				} else {
					countNum = startInt + limitInt;
					if (countNum > count) {
						countNum = count;
					}
				}
				for (int i = startInt; i < countNum; i++) {
					BundleInfo mv = tempList.get(i);
					dataList.add(mv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JsonHelper.encodeList2JSONArray(dataList, count, null);
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String start() {
		changeStatus("START");
		return null;
	}

	public String stop() {
		changeStatus("STOP");
		return null;
	}
	
	public String refresh() {
		changeStatus("REFRESH");
		return null;
	}
	
	public String uninstall() {
		changeStatus("UNINSTALL");
		return null;
	}
	
	public String update() {
		changeStatus("UPDATE");
		return null;
	}
	
	public void changeStatus(String oper) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObject = new JSONObject();

		try {
			String hostIp = request.getParameter("hostIp");
			String hostPort = request.getParameter("hostPort");
			String bundleId = request.getParameter("id");
			if ("START".equals(oper)) {
				BundleAdminClient.getBundleAdmin(hostIp, hostPort).startBundle(bundleId);
			} else if ("STOP".equals(oper)) {
				BundleAdminClient.getBundleAdmin(hostIp, hostPort).stopBundle(bundleId);
			} else  if("REFRESH".equals(oper)) {
				BundleAdminClient.getBundleAdmin(hostIp, hostPort).refreshBundle(bundleId);
			} else  if("UPDATE".equals(oper)) {
				BundleAdminClient.getBundleAdmin(hostIp, hostPort).updateBundle(bundleId);
			} else  if("UNINSTALL".equals(oper)) {
				BundleAdminClient.getBundleAdmin(hostIp, hostPort).uninstallBundle(bundleId);
			}

			jsonObject.element("success", true);
			jsonObject.element("msg", "操作成功");
		} catch (Exception e) {
			log.error("bundle oper exception: ", e);

			jsonObject.element("success", false);
			jsonObject.element("msg", "操作失败，失败原因：" + e.getMessage());
		}

		Utils.PrintWrite(response, jsonObject.toString());
	}

	public String upload() {
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject jsonObject = new JSONObject();

		try {
//			UserInfoVo userInfoVo = super.getSessionContainer(request)
//					.getUserInfo();
			String hostIp = request.getParameter("hostIp");
			String hostPort = request.getParameter("hostPort");
			String bundleType = request.getParameter("bundleType");
			String bundleId = request.getParameter("bundleId");
			String startFlag = request.getParameter("startFlag");
			// request.setCharacterEncoding("GBK");
			String fname=URLDecoder.decode(uploadFileName, "utf-8");
//			request.setCharacterEncoding(PropertiesContext.getProjectEncoding());
//			String fname = URLDecoder.decode(fileName,
//					PropertiesContext.getProjectEncoding());

			if (fname.indexOf("\\") != -1) {
				fname = fname.substring(fname.lastIndexOf("\\") + 1,
						fname.length());
			}
			String bundlePath = "";
			if("0".equals(bundleType)) {
				bundlePath = "L0003";
			} else if("1".equals(bundleType)) {
				bundlePath = "L0004";
			} else if("2".equals(bundleType)) {
				bundlePath = "L0005";
			} else if("3".equals(bundleType)) {
				bundlePath = "L0006";
			} else if("4".equals(bundleType)) {
				bundlePath = "L0006";
			}
			String url = bundlePath + "/" + fname;
			FileTransferClient.uploadFile(hostIp, hostPort, upload, url);
		
			if(bundleId != null && !"".equals(bundleId)) {
				BundleAdminClient.getBundleAdmin(hostIp, hostPort).replaceBundle(bundleId, url);
			} else {
				if("true".equals(startFlag)) {
					BundleAdminClient.getBundleAdmin(hostIp, hostPort).installAndStartBundle(url);
				} else {
					BundleAdminClient.getBundleAdmin(hostIp, hostPort).installBundle(url);
				}
			}
			jsonObject.put("success", "true");
			jsonObject.element("msg", "操作成功");
		} catch (Exception e) {
			log.error("bundle upload error: ", e);

			jsonObject.put("success", "false");
			jsonObject.element("msg", "操作失败，失败原因：" + e.getMessage());
		}
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public JSON getResultJson() {
		return resultJson;
	}

	public void setResultJson(JSON resultJson) {
		this.resultJson = resultJson;
	}

}
