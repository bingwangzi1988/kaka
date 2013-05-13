package com.common.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.rif.util.WebUtils;

import com.common.base.BaseAction;
import com.common.util.ExportExcelUtils;
import com.common.util.PorpertiesUtil;
import com.common.util.Utils;
import com.opms.conmg.po.Cmbank;
import com.opms.conmg.service.CmbankService;
import com.opms.conmg.service.CmparmService;
import com.opms.conmg.vo.CmbankQuery;

/**
 * excel报表生成及文件下载
 * 
 * @author huan.li
 * @date 2011/11/9
 */
public class ExportExcelAction extends BaseAction {
	private static Log log = LogFactory.getLog(BaseAction.class);

	CmparmService cmparmService = (CmparmService) SpringBeanLoader.getSpringBean("cmparmService");
//	TicDpBatService ticDpBatService = (TicDpBatService) SpringBeanLoader.getSpringBean("ticDpBatService");
//
//	CmhostconfService cmhostconfService = (CmhostconfService) SpringBeanLoader.getSpringBean("cmhostconfService");
//
//	TicOfflistBook1Service ticOfflistBook1Service = (TicOfflistBook1Service) SpringBeanLoader.getSpringBean("ticOfflistBook1Service");

	CmbankService cmbankService = (CmbankService) SpringBeanLoader.getSpringBean("cmbankService");

//	CmchaninfoService cmchaninfoService = (CmchaninfoService) SpringBeanLoader.getSpringBean("cmchaninfoService");
//
//	TicPrecardPoolService ticPrecardPoolService = (TicPrecardPoolService) SpringBeanLoader.getSpringBean("ticPrecardPoolService");
//
//	private TicOfflistBookhisService ticOfflistBookhisService = (TicOfflistBookhisService) SpringBeanLoader.getSpringBean("ticOfflistBookhisService");

	private static final long serialVersionUID = 1649482035443202756L;
	private JSON resultJson;

	public JSON getResultJson() {
		return resultJson;
	}

	public void setResultJson(JSON resultJson) {
		this.resultJson = resultJson;
	}

	// 导出数据准备预处理表
	public String exportTicDpProc() throws Exception {
		JSONObject jsonObject = new JSONObject();
		Properties props = PorpertiesUtil.getProperties("fileDownload.properties");
		HttpServletRequest request = ServletActionContext.getRequest();
		String gridStore = request.getParameter("gridStore");
		// List<LinkedHashMap<String, Object>> result = getData(gridStore);
		List<LinkedHashMap<String, Object>> result = new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> gridDatas = getData(gridStore);
		if (gridDatas != null && gridDatas.size() > 0) {
			for (LinkedHashMap<String, Object> obj : gridDatas) {
				LinkedHashMap<String, Object> rs = new LinkedHashMap<String, Object>();
				for (String key : obj.keySet()) {
					if (obj.get(key).equals("&nbsp;")) {
						rs.put(key, "");
					} else {
						rs.put(key, obj.get(key));
					}
				}
				result.add(rs);
			}
		}
		String[] headers = { "申请日期", "申请机构", "发卡批次号", "卡号", "处理状态", "卡种", "失效日期" };
		String[] columns = { "applyDate", "applyBrc", "batNo", "cardNo", "procFlag", "cardKind", "exDate" };
		String fileNm = "制卡明细列表" + parseDate(new Date()) + ".xls";
		File file = new File(props.get("downloadFilePath") + fileNm);
		OutputStream out = new FileOutputStream(file);
		ExportExcelUtils.exportExcel("制卡明细列表", headers, columns, result, out, "");
		out.flush();
		out.close();
		jsonObject.put("fileNm", props.get("downloadFilePath") + fileNm);
		setResultJson(jsonObject);
		return SUCCESS;
	}


	// 下载Excel
	public String downloadExcel() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileNm = request.getParameter("fileNm");
		if (fileNm != null && fileNm.trim().length() > 0) {
			fileNm = URLDecoder.decode(fileNm, "utf-8");
		}
		log.info("download... " + fileNm);
		executeDownload(fileNm);
		return null;

	}

	// 组织数据
	@SuppressWarnings("unchecked")
	private List<LinkedHashMap<String, Object>> getData(String gridStore) {
		JSONArray array = JSONArray.fromObject(gridStore);
		List<LinkedHashMap<String, Object>> result = new ArrayList<LinkedHashMap<String, Object>>();
		for (Object obj : array) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			JSONObject jsonObject = (JSONObject) obj;
			Iterator<String> it = jsonObject.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if ((!"undefined".equals(key)) && (!"utilityId".equals(key))) {
					map.put(key, ("null").equals(jsonObject.get(key).toString().trim()) ? "" : jsonObject.get(key));
				}
			}
			result.add(map);
		}

		return result;
	}

	private String parseDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	// 打包制卡文件
	public String zipPerFile() throws Exception {
		JSONObject jsonObject = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String batNo = request.getParameter("batNo");
			String batDate = request.getParameter("batDate");
			String brcNo = request.getParameter("brcNo");
			batDate = batDate.replaceAll("-", "");
			Properties props = PorpertiesUtil.getProperties("fileDownload.properties");
			String filePath = props.get("genFilePath").toString();
			// String docNm = props.get("perDoc").toString();
			// filePath="c:";
			// String fileNm = filePath + File.separator + brcNo + "_" + batNo
			// + ".tar";
			// System.out.println("filePath===" + fileNm);
			// String shellNm = props.get("perShellNm").toString();
			// String pwd = EncodeUtils.decode(cmparmService.getPwd());
			// tarFiles(shellNm, filePath, batDate, batNo, docNm, pwd);
			// executeDownload(fileNm);

			String fileName = request.getParameter("fileName");
			String fileNm = filePath + File.separator + fileName;
			File file = new File(fileNm);
			if (file.exists()) {
				jsonObject.put("success", true);
				jsonObject.put("fileNm", fileNm);
			} else {

				jsonObject.put("failure", true);
				jsonObject.put("msg", "文件不存在!文件路径" + fileNm);
			}
		} catch (Exception e) {
			jsonObject.put("failure", true);
			jsonObject.put("msg", e.getMessage());
		}
		setResultJson(jsonObject);
		return SUCCESS;
	}

	// 打包制卡文件
	public String getReportFilepath() throws Exception {
		JSONObject jsonObject = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String fileName = request.getParameter("fileName");
			Properties props = PorpertiesUtil.getProperties("fileDownload.properties");
			String filePath = props.get("reportFilePath").toString();
			String fileNm = filePath + File.separator + fileName;
			System.out.println("filePath===" + fileNm);
			File file = new File(fileNm);
			if (file.exists()) {
				jsonObject.put("success", true);
				jsonObject.put("fileNm", fileNm);
			} else {
				jsonObject.put("failure", true);
				jsonObject.put("msg", "文件不存在!文件路径" + fileNm);
			}
		} catch (Exception e) {
			jsonObject.put("failure", true);
			jsonObject.put("msg", e.getMessage());
		}
		setResultJson(jsonObject);
		return SUCCESS;
	}

	// 打包结果文件
	public String tarRspFile() throws Exception {
		JSONObject jsonObject = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String batNo = request.getParameter("batNo");
			String batDate = request.getParameter("batDate");
			batDate = batDate.replaceAll("-", "");
			Properties props = PorpertiesUtil.getProperties("fileDownload.properties");
			String filePath = props.get("genFilePath").toString();
			String docNm = props.get("rspDoc").toString();
			String fileNm = filePath + batDate + File.separator + batNo + File.separator + docNm + File.separator + batNo + ".tar";
			String shellNm = props.get("rspShellNm").toString();
			tarFiles(shellNm, filePath, batDate, batNo, docNm, "");
			jsonObject.put("success", true);
			jsonObject.put("fileNm", fileNm);
		} catch (Exception e) {
			jsonObject.put("failure", true);
			jsonObject.put("msg", e.getMessage());
		}
		setResultJson(jsonObject);
		return SUCCESS;
	}

	/**
	 * 
	 * @param shellNm
	 *            打包脚本名称
	 * @param filePath
	 *            文件路径
	 * @param batDate
	 *            申请日期
	 * @param batNo
	 *            批次号
	 * @param docNm
	 *            打包文件类型
	 * @param pwd
	 *            加密密码（供制卡文件用）
	 * @throws IOException
	 */
	private void tarFiles(String shellNm, String filePath, String batDate, String batNo, String docNm, String pwd) throws IOException {
		String tarDocNm = filePath + batDate + File.separator + batNo + File.separator + docNm;
		log.info("tarFiles... " + tarDocNm);
		String[] command = { shellNm, tarDocNm, batNo + ".tar", pwd };
		Process pWin = Runtime.getRuntime().exec(command);
		InputStream in = pWin.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		InputStream error = pWin.getErrorStream();
		BufferedReader brout = new BufferedReader(new InputStreamReader(error));
		String sLine = "";
		while ((sLine = br.readLine()) != null) {
			// log.info("执行正确输出:" + sLine);
		}
		while ((sLine = brout.readLine()) != null) {
			log.info("执行错误输出:" + sLine);
		}
		in.close();
		error.close();
		br.close();
		brout.close();
		pWin.destroy();
	}

	// 执行下载
	public void executeDownload(String fileNm) throws Exception {
		File file = new File(fileNm);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "utf-8") + ";");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			bout.write(temp, 0, size);
		}
		in.close();
		ServletOutputStream os = response.getOutputStream();
		os.write(bout.toByteArray());
		os.flush();
		os.close();
	}
}
