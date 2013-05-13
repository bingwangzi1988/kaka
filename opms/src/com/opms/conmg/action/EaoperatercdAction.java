package com.opms.conmg.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
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
import com.opms.conmg.po.Eaoperatercd;
import com.opms.conmg.service.EaoperatercdService;
import com.opms.conmg.vo.EaoperatercdQuery;

public class EaoperatercdAction extends BaseAction implements Preparable,
		ModelDriven {

	EaoperatercdService eaoperatercdService = (EaoperatercdService) SpringBeanLoader
			.getSpringBean("eaoperatercdService");

	private JSON resultJson;

	private Eaoperatercd eaoperatercd;

	public JSON getResultJson() {
		return resultJson;
	}

	public void setResultJson(JSON resultJson) {
		this.resultJson = resultJson;
	}

	public void prepare() throws Exception {
		eaoperatercd = new Eaoperatercd();
	}

	public Object getModel() {
		return eaoperatercd;
	}

	public String list() {
		CountOrder countOrder = CountOrderUtil.getCountOrder();

		EaoperatercdQuery eaoperatercdQuery = QueryUitl
				.newQuery(EaoperatercdQuery.class);
		List<Eaoperatercd> list = eaoperatercdService.searchEaoperatercd(
				eaoperatercdQuery, countOrder);
		Long pageCount = eaoperatercdService
				.countEaoperatercd(eaoperatercdQuery);

		JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list, pageCount
				.intValue(), "yyyy-MM-dd HH:mm:ss");
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String find() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = new String(request.getParameter("id"));
		Eaoperatercd eaoperatercd = eaoperatercdService.getById(id);

		JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(
				eaoperatercd, null);
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public void save() {
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject params = JSONObject.fromObject(request
				.getParameter("params"));
		String operateObject = params.get("operateObject").toString();// 操作对象
		if (!operateObject.equals("Eaoperatercd")) {
			String userId = params.get("userId")+"";// 授权员工号
			String authoriseId = params.get("authoriseId")+"";// 授权员工号
			String operateType = params.get("operateType")+"";// 操作类型
			String operateInfo = "";// 操作信息

			Eaoperatercd eaoperatercd = new Eaoperatercd();
			eaoperatercd.setUserid(userId);
			eaoperatercd.setAuthorizerid(authoriseId);
			eaoperatercd.setOperatedate(new Date());
			eaoperatercd.setOperateinfo(operateInfo);
			eaoperatercd.setOperatetype(operateType);
			eaoperatercd.setOperateobject(operateObject);
			eaoperatercdService.save(eaoperatercd);
		}
	}

	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String ids = request.getParameter("ids");
		String[] idarray = ids.split(",");

		JSONObject returnObject = new JSONObject();
		try {
			for (int i = 0; i < idarray.length; i++) {
				String id = new String(idarray[i]);
				eaoperatercdService.removeById(id);
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
}
