package com.opms.monitor.action;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.report.fcf.FcfDataMapper;
import org.eredlab.g4.rif.report.fcf.GraphConfig;
import org.eredlab.g4.rif.report.fcf.Set;

import sun.management.ManagementFactory;

import com.common.base.BaseAction;
import com.common.util.G4Constants;
import com.common.util.Utils;
import com.sun.management.OperatingSystemMXBean;

/**
 * ��ȡ��������Ϣ���ڴ�CPUʵʱ���
 * 
 */
public class ServerInfoAction extends BaseAction {

	private static final long serialVersionUID = 4682220274726602957L;
	
	private JSON resultJson;
	
	public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }
	
	private static final int CPUTIME = 500;
	private static final int PERCENT = 100;
	private static final int FAULTLENGTH = 10;

	/**
	 * ҳ���ʼ��
	 * 
	 */
//	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//		removeSessionAttribute(request, "JVM_MEM_LIST");
//		removeSessionAttribute(request, "HOST_MEM_LIST");
//		removeSessionAttribute(request, "HOST_CPU_LIST");
//		InetAddress localhost = InetAddress.getLocalHost();
//		Dto outDto = new BaseDto();
//		outDto.put("a.����ϵͳ", System.getProperty("os.name") + "_" + System.getProperty("os.arch"));
//		outDto.put("b.����IP", "" + localhost.getHostAddress());
//		outDto.put("c.Ӧ�÷�����", getServlet().getServletContext().getServerInfo());
//		outDto.put("d.�����˿�", request.getServerPort());
//		outDto.put("e.Web��·��", getServlet().getServletContext().getRealPath("/"));
//		outDto.put("f.Servlet�汾", getServlet().getServletContext().getMajorVersion() + "."
//				+ getServlet().getServletContext().getMinorVersion());
//		outDto.put("g.JVM�汾", System.getProperty("java.version"));
//		outDto.put("h.JVM�ṩ��", System.getProperty("java.vendor"));
//		outDto.put("i.JVM��װ·��", System.getProperty("java.home"));
//		outDto.put("j.���������ڴ�", osmxb.getTotalSwapSpaceSize() / 1024 / 1024 + "M");
//		outDto.put("k.JVM��������ڴ�", Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
//		request.setAttribute("jsonInfo", outDto.toJson());
//
//		String caption = "JVM�ڴ�ʹ�����ʵʱ���ͼ(";
//		caption = caption + "�ɷ������ڴ�:" + new Double(Runtime.getRuntime().maxMemory() / 1024 / 1024).intValue() + "M ";
//		caption = caption + "�ѷ������ڴ�:" + new Double(Runtime.getRuntime().totalMemory() / 1024 / 1024).intValue() + "M ";
//		caption = caption
//				+ "�����ڴ�:"
//				+ new Double((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024)
//						.intValue() + "M)";
//		// updateJvmChart(mapping, form, request, response);
//		return mapping.findForward("serverInfoView");
//	}
	
	/**
	 * ����CPU���ͼ
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String updateCpuChart() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		GraphConfig graphConfig = new GraphConfig();
		graphConfig.put("divLineColor", "44AF31");
		graphConfig.put("divLineAlpha", "100");
		graphConfig.put("showAlternateHGridColor", "1");
		graphConfig.put("AlternateHGridColor", "54D421");
		graphConfig.put("alternateHGridAlpha", "20");
		graphConfig.put("numberSuffix", "M");
		graphConfig.put("anchorRadius", "3");
		graphConfig.put("numberSuffix", "%");
		graphConfig.put("decimalPrecision", "0");
		graphConfig.put("limitsDecimalPrecision", "0");

		List dataList = (ArrayList) getSessionAttribute(request, "HOST_CPU_LIST");
		if (G4Utils.isEmpty(dataList)) {
			dataList = new ArrayList();
		}
		Set set0 = new Set();
		set0.setName(G4Utils.getCurrentTime("hh:mm:ss"));
		set0.setValue(new BigDecimal(getCpuRatioForWindows()).intValue() + "");
		set0.setColor(G4Constants.CHART_COLORS[G4Utils.getRandom(0, 10).intValue()]);
		dataList.add(set0);
		while (dataList.size() > 5) {
			dataList.remove(dataList.size() - 5);
		}

		setSessionAttribute(request, "HOST_CPU_LIST", dataList);
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("xmlstring_cpu", xmlString);
		Utils.PrintWrite(response, JsonHelper.encodeObject2Json(outDto));
//		write(JsonHelper.encodeObject2Json(outDto), response);
		return SUCCESS;
	}

	/**
	 * �������������ڴ���ͼ
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String updateHostMemChart() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		GraphConfig graphConfig = new GraphConfig();
		graphConfig.put("divLineColor", "44AF31");
		graphConfig.put("divLineAlpha", "100");
		graphConfig.put("showAlternateHGridColor", "1");
		graphConfig.put("AlternateHGridColor", "54D421");
		graphConfig.put("alternateHGridAlpha", "20");
		graphConfig.put("numberSuffix", "M");
		graphConfig.put("anchorRadius", "3");
		graphConfig.put("numberSuffix", "%");
		graphConfig.put("decimalPrecision", "0");
		graphConfig.put("limitsDecimalPrecision", "0");

		List dataList = (ArrayList) getSessionAttribute(request, "HOST_MEM_LIST");
		if (G4Utils.isEmpty(dataList)) {
			dataList = new ArrayList();
		}
		Set set0 = new Set();
		set0.setName(G4Utils.getCurrentTime("hh:mm:ss"));

		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
		Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
		set0.setValue(compare.intValue() + "");
		set0.setColor(G4Constants.CHART_COLORS[G4Utils.getRandom(0, 10).intValue()]);
		dataList.add(set0);
		while (dataList.size() > 5) {
			dataList.remove(dataList.size() - 5);
		}

		setSessionAttribute(request, "HOST_MEM_LIST", dataList);
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("xmlstring_hostmem", xmlString);
		Utils.PrintWrite(response, JsonHelper.encodeObject2Json(outDto));
//		write(JsonHelper.encodeObject2Json(outDto), response);
		return SUCCESS;
	}
	
	/**
	 * ����JVM�ڴ���ͼ
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String updateJvmChart() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String caption = "JVM�ڴ�ʹ�����ʵʱ���ͼ(";
		caption = caption + "�ɷ������ڴ�:" + new Double(Runtime.getRuntime().maxMemory() / 1024 / 1024).intValue() + "M ";
		caption = caption + "�ѷ������ڴ�:" + new Double(Runtime.getRuntime().totalMemory() / 1024 / 1024).intValue() + "M ";
		caption = caption
				+ "�����ڴ�:"
				+ new Double((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024)
						.intValue() + "M)";
		GraphConfig graphConfig = new GraphConfig();
		graphConfig.setCaption(caption);
		graphConfig.put("divLineColor", "008ED6");
		graphConfig.put("divLineAlpha", "20");
		graphConfig.put("showAlternateHGridColor", "1");
		graphConfig.put("AlternateHGridColor", "BFFFFF");
		graphConfig.put("alternateHGridAlpha", "20");
		graphConfig.put("numberSuffix", "M");
		graphConfig.put("decimalPrecision", "0");
		graphConfig.put("limitsDecimalPrecision", "0");
		graphConfig.put("anchorRadius", "3");
		graphConfig.setXAxisName("%(�����ڴ�/�ѷ�������ڴ�)");

		List dataList = (ArrayList) getSessionAttribute(request, "JVM_MEM_LIST");
		if (G4Utils.isEmpty(dataList)) {
			dataList = new ArrayList();
		}
		long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		Set set0 = new Set();
		set0.setName(new BigDecimal(used)
				.divide(new BigDecimal(Runtime.getRuntime().totalMemory()), 2, BigDecimal.ROUND_HALF_UP)
				.multiply(new BigDecimal(100)).intValue()
				+ "%");

		set0.setValue(new Double(used / 1024 / 1024).intValue() + "");
		set0.setColor(G4Constants.CHART_COLORS[G4Utils.getRandom(0, 10).intValue()]);
		dataList.add(set0);
		while (dataList.size() > 10) {
			dataList.remove(dataList.size() - 10);
		}
		setSessionAttribute(request, "JVM_MEM_LIST", dataList);
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("xmlstring", xmlString);
		Utils.PrintWrite(response, JsonHelper.encodeObject2Json(outDto));
//		write(JsonHelper.encodeObject2Json(outDto), response);
		return SUCCESS;
	}

	public static String getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir")
					+ "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime)).intValue() + "";
			} else {
				return 0 + "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0 + "";
		}
	}

	private static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// �ֶγ���˳��Caption,CommandLine,KernelModeTime,ReadOperationCount,
				// ThreadCount,UserModeTime,WriteOperation
				String caption = substring(line, capidx, cmdidx - 1).trim();
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				String s1 = substring(line, kmtidx, rocidx - 1).trim();
				String s2 = substring(line, umtidx, wocidx - 1).trim();
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					if (s1.length() > 0)
						idletime += Long.valueOf(s1).longValue();
					if (s2.length() > 0)
						idletime += Long.valueOf(s2).longValue();
					continue;
				}
				if (s1.length() > 0)
					kneltime += Long.valueOf(s1).longValue();
				if (s2.length() > 0)
					usertime += Long.valueOf(s2).longValue();
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}

}
