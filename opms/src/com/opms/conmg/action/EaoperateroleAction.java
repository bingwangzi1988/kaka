package com.opms.conmg.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.rif.util.WebUtils;

import com.common.base.BaseAction;
import com.common.business.commonbus.service.CommonService;
import com.common.util.CountOrder;
import com.common.util.CountOrderUtil;
import com.common.util.QueryUitl;
import com.common.util.Utils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opms.conmg.po.Eaoperaterole;
import com.opms.conmg.service.EaoperateroleService;
import com.opms.conmg.vo.EaoperateroleQuery;

public class EaoperateroleAction extends BaseAction implements Preparable,
		ModelDriven {

	EaoperateroleService eaoperateroleService = (EaoperateroleService) SpringBeanLoader
			.getSpringBean("eaoperateroleService");
	CommonService commonService = (CommonService) SpringBeanLoader
			.getSpringBean("commonService");

	private JSON resultJson;

	private Eaoperaterole eaoperaterole;

	public JSON getResultJson() {
		return resultJson;
	}

	public void setResultJson(JSON resultJson) {
		this.resultJson = resultJson;
	}

	public void prepare() throws Exception {
		eaoperaterole = new Eaoperaterole();
	}

	public Object getModel() {
		return eaoperaterole;
	}

	public String list() {
		CountOrder countOrder = CountOrderUtil.getCountOrder();

		EaoperateroleQuery eaoperateroleQuery = QueryUitl
				.newQuery(EaoperateroleQuery.class);
		HttpServletRequest request = ServletActionContext.getRequest();
		UserInfoVo userInfoVo = WebUtils.getSessionContainer(request).getUserInfo();
		boolean isRole=false;
		if("developer".equals(userInfoVo.getAccount())){
			isRole=true;
		}
		
		JSONArray jsonArray = new JSONArray();
		getRoleMenu(request, "EACODE", "field", "code", "codedesc", "MENUTOTABLE",
				null, "code", "asc", null, jsonArray);
		List<String> menulst=new ArrayList<String>();
		if(jsonArray!=null && jsonArray.size()>0){
			for(int i=0;i<jsonArray.size();i++){
				JSONObject obj=jsonArray.getJSONObject(i);
				menulst.add(obj.get("code")+"");
			}
		}
		
		List<Eaoperaterole> list = eaoperateroleService.searchEaoperaterole(
				eaoperateroleQuery, countOrder,isRole,menulst);
		Long pageCount = eaoperateroleService
				.countEaoperaterole(eaoperateroleQuery,isRole,menulst);

		JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list, pageCount
				.intValue(), null);
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String find() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = new String(request.getParameter("id"));
		Eaoperaterole eaoperaterole = eaoperateroleService.getById(id);

		JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(
				eaoperaterole, null);
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String save() {
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject returnObject = new JSONObject();
		
		try {
			if (isEmpty(eaoperaterole.getId()))
				eaoperaterole.setId(null);
			boolean isEdit = Boolean.parseBoolean(request
					.getParameter("dataEditflag"));
			if (!isEdit) {
				Eaoperaterole role = eaoperateroleService
						.searchEaoperateroleByTablename(eaoperaterole
								.getTablename());
				if (role != null) {
					returnObject.put("SUCCESS", false);
					returnObject.put("msg", "该配置已存在");
					setResultJson(returnObject);
					return SUCCESS;
				}
			}
			if (isEdit)
				eaoperateroleService.update(eaoperaterole);
			else
				eaoperateroleService.save(eaoperaterole);
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
				eaoperateroleService.removeById(id);
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

	public String getRole() {
		JSONObject jsonObject = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		String operateObject = request.getParameter("operateObject");// 操作对象
		Eaoperaterole role = eaoperateroleService
				.searchEaoperateroleByTablename(operateObject);

		if (role != null) {
			JSONObject operateParams = new JSONObject();
			operateParams.put("tableName", role.getTablename());
			operateParams.put("insertFlag", role.getInsertflag());
			operateParams.put("updateFlag", role.getUpdateflag());
			operateParams.put("deleteFlag", role.getDeleteflag());
			System.out.println(operateParams.toString());
			jsonObject.put("success", true);
			jsonObject.put("msg", operateParams);
		} else {
			jsonObject.put("failure", true);
			jsonObject.put("msg", "菜单权限未配置");
		}
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String getRoleMenu() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tableName = request.getParameter("tableName");
		String field = request.getParameter("field");
		String code = request.getParameter("code");
		String codedesc = request.getParameter("codedesc");
		String fieldvalue = request.getParameter("fieldvalue");
		String valueflagvalue = request.getParameter("valueflagvalue");
		String orderby = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");
		String valueflag = request.getParameter("valueflag");

		JSONArray jsonArray = new JSONArray();
		getRoleMenu(request, tableName, field, code, codedesc, fieldvalue,
				valueflagvalue, orderby, ordertype, valueflag, jsonArray);
		HttpServletResponse response=ServletActionContext.getResponse();
		Utils.PrintWrite(response, jsonArray.toString());
		return SUCCESS;
	}

	private void getRoleMenu(HttpServletRequest request, String tableName,
			String field, String code, String codedesc, String fieldvalue,
			String valueflagvalue, String orderby, String ordertype,
			String valueflag, JSONArray jsonArray) {
		UserInfoVo userInfoVo = WebUtils.getSessionContainer(request)
				.getUserInfo();
		List grantList = eaoperateroleService.searchUserRole(userInfoVo
				.getUserid());

		if (valueflagvalue != null && valueflagvalue.trim().length() > 0)
			valueflagvalue = valueflagvalue.trim();
		else
			valueflagvalue = "";

		List list = commonService.getForeignLinks(tableName, field, code,
				codedesc, fieldvalue, orderby, ordertype);
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			boolean isRole=checkGeant(grantList, (String)objects[0]);
			if("Eaoperaterole".equalsIgnoreCase((String)objects[0]) && !"developer".equals(userInfoVo.getAccount())){
				isRole=false;
			}
			if (isRole || "developer".equals(userInfoVo.getAccount())) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.element("code", objects[0]);
				Object obj_value = objects[1];
				if (valueflag != null && valueflag.trim().length() > 0) {
					if (valueflag.trim().toLowerCase().equals("0"))// 返回的codedesc=codedesc+符号+code
						obj_value = objects[1] + valueflagvalue + objects[0];
					if (valueflag.trim().toLowerCase().equals("1"))// 返回的codedesc=code+符号+codedesc
						obj_value = objects[0] + valueflagvalue + objects[1];
				}
				jsonObj.element("codedesc", obj_value);
				jsonArray.element(jsonObj);
			}
		}
	}

	private Boolean checkGeant(List grantList, String menuName) {
		Boolean result = new Boolean(false);
		for (int i = 0; i < grantList.size(); i++) {
			String request = (String) grantList.get(i);
			if (request != null && request.lastIndexOf(".jsp") != -1) {
				String tableName = request.substring(
						request.lastIndexOf("/") + 1, request
								.lastIndexOf(".jsp"));
				if (menuName.equalsIgnoreCase(tableName)) {
					result = new Boolean(true);
				}
			}
		}
		return result;
	}
}
