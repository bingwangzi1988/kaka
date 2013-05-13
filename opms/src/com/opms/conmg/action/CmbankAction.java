package com.opms.conmg.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;

import com.common.base.BaseAction;
import com.common.util.CountOrder;
import com.common.util.CountOrderUtil;
import com.common.util.QueryUitl;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opms.conmg.po.Cmbank;
import com.opms.conmg.service.CmbankService;
import com.opms.conmg.vo.CmbankQuery;

public class CmbankAction extends BaseAction implements Preparable, ModelDriven {

	CmbankService cmbankService = (CmbankService) SpringBeanLoader
			.getSpringBean("cmbankService");

	private JSON resultJson;

	private Cmbank cmbank;

	private JSONArray resultArray;

	public JSONArray getResultArray() {
		return resultArray;
	}

	public void setResultArray(JSONArray resultArray) {
		this.resultArray = resultArray;
	}

	public JSON getResultJson() {
		return resultJson;
	}

	public void setResultJson(JSON resultJson) {
		this.resultJson = resultJson;
	}

	public void prepare() throws Exception {
		cmbank = new Cmbank();
	}

	public Object getModel() {
		return cmbank;
	}

	public String list() {
		CountOrder countOrder = CountOrderUtil.getCountOrder();

		CmbankQuery cmbankQuery = QueryUitl.newQuery(CmbankQuery.class);
		List<Cmbank> list = cmbankService.searchCmbank(cmbankQuery, countOrder);
		Long pageCount = cmbankService.countCmbank(cmbankQuery);

		JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list, pageCount
				.intValue(), null);
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String find() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = new String(request.getParameter("id"));
		Cmbank cmbank = cmbankService.getById(id);

		JSONObject jsonObject = JsonHelper
				.encodeObject2JSONObject(cmbank, null);
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String save() {
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject returnObject = new JSONObject();
		try {
			if (isEmpty(cmbank.getId()))
				cmbank.setId(null);
			String unique0 = cmbankService.isUnique(cmbank, "branch", "分行");
			if (unique0 != null) {
				returnObject.put("SUCCESS", false);
				returnObject.put("msg", unique0);
				setResultJson(returnObject);
				return SUCCESS;
			}
			boolean isEdit = Boolean.parseBoolean(request
					.getParameter("dataEditflag"));
			if (isEdit)
				cmbankService.update(cmbank);
			else
				cmbankService.save(cmbank);
			returnObject.element(SUCCESS, true);
			returnObject.element("msg", "操作成功");

		} catch (Exception e) {
			returnObject.put("failure", true);
			returnObject.put("msg", e.getMessage());
			e.printStackTrace();
		}

		setResultJson(returnObject);
		return SUCCESS;
	}

	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String ids = request.getParameter("ids");
		String[] idarray = ids.split(",");

		JSONObject returnObject = new JSONObject();
		try {
			for (int i = 0; i < idarray.length; i++) {
				String id = new String(idarray[i]);
				cmbankService.removeById(id);
			}
			returnObject.put("success", true);
			returnObject.put("msg", "删除成功");
		} catch (Exception e) {
			returnObject.put("failure", true);
			returnObject.put("msg", e.getMessage());
			e.printStackTrace();
		}
		setResultJson(returnObject);
		return SUCCESS;
	}

	/**
	 * 查询出所有分行信息，并将分行号截取出来 注：分行为：2位分行号+ 00
	 * */
	public String getBranch() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List lst = cmbankService.searchCmbank();
		if (lst != null && lst.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < lst.size(); i++) {
				Cmbank cb = (Cmbank) lst.get(i);
				JSONObject jsonObject = new JSONObject();
				String bra = cb.getBranch().substring(0, 2);
				jsonObject.element("code", bra);
				jsonObject.element("codedesc", cb.getBranchName() + "(" + bra
						+ ")");
				jsonArray.element(jsonObject);
			}
			response.setCharacterEncoding("utf-8");
			PrintWriter pw;
			try {
				pw = response.getWriter();
				pw.write(jsonArray.toString());
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// setResultArray(jsonArray);
		}
		return null;
	}

	/**
	 * 查询出所有总行,分行信息
	 * */
	public String getBrchCode() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String valueflag = request.getParameter("valueflag");
		String valueflagvalue = request.getParameter("valueflagvalue");
		String orderby = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");
		String split = request.getParameter("split");

		List<Cmbank> lst = cmbankService.getBrchCode(orderby, ordertype);
		if (lst != null && lst.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (Cmbank cb : lst) {
				JSONObject jsonObject = new JSONObject();
				String bra = cb.getBranch();
				String branchName = cb.getBranchName();
				if (split != null && split.trim().length() > 0) {
					if ("1".equals(split)) {
						if (bra.length() > 2)
							bra = bra.substring(0, 2);
					}
				}
				if (valueflag != null && valueflag.trim().length() > 0) {
					if (valueflag.trim().toLowerCase().equals("0"))// 返回的codedesc=codedesc+符号+code
						branchName = branchName + valueflagvalue + bra;
					if (valueflag.trim().toLowerCase().equals("1"))// 返回的codedesc=code+符号+codedesc
						branchName = bra + valueflagvalue + branchName;
				}
				jsonObject.element("code", bra);
				jsonObject.element("codedesc", branchName);
				jsonArray.element(jsonObject);
			}
			response.setCharacterEncoding("utf-8");
			PrintWriter pw;
			try {
				pw = response.getWriter();
				pw.write(jsonArray.toString());
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// setResultArray(jsonArray);
		}
		return null;
	}

}
