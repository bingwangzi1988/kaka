package com.common.business.commonbus.action;

import com.common.business.commonbus.service.CommonService;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.rif.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: ritchrs Date: 11-6-28 Time: 下午5:27 desc:
 */
public class CodeSelectAction extends ActionSupport {

	CommonService commonService = (CommonService) SpringBeanLoader.getSpringBean("commonService");

	private JSONArray resultArray;

	public JSONArray getResultArray() {
		return resultArray;
	}

	public void setResultArray(JSONArray resultArray) {
		this.resultArray = resultArray;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tableName = request.getParameter("tableName");
		String field = request.getParameter("field");
		String code = request.getParameter("code");
		String codedesc = request.getParameter("codedesc");
		String fieldvalue = request.getParameter("fieldvalue");
		String valueflag = request.getParameter("valueflag");
		String valueflagvalue = request.getParameter("valueflagvalue");
		String orderby=request.getParameter("orderby");
		String ordertype=request.getParameter("ordertype");
		String addCode=request.getParameter("addcode");
		String addCodeValue=request.getParameter("addcodevalue");
		
		if (valueflagvalue != null && valueflagvalue.trim().length() > 0)
			valueflagvalue =valueflagvalue.trim();
		else
			valueflagvalue = "";

		List list = commonService.getForeignLinks(tableName, field, code, codedesc, fieldvalue,orderby, ordertype);
		JSONArray jsonArray = new JSONArray();
		
		if(addCode!=null && addCode.trim().length()>0 && addCodeValue!=null && addCodeValue.trim().length()>0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("code", addCode);
			if (valueflag != null && valueflag.trim().length() > 0) {
				if (valueflag.trim().toLowerCase().equals("0"))// 返回的codedesc=codedesc+符号+code
					addCodeValue = addCodeValue + valueflagvalue + addCode;
				if (valueflag.trim().toLowerCase().equals("1"))// 返回的codedesc=code+符号+codedesc
					addCodeValue = addCode + valueflagvalue + addCodeValue;
			}
			jsonObject.element("codedesc", addCodeValue);
			jsonArray.element(jsonObject);
		}
		
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("code", objects[0]);
			Object obj_value = objects[1];
			if (valueflag != null && valueflag.trim().length() > 0) {
				if (valueflag.trim().toLowerCase().equals("0"))// 返回的codedesc=codedesc+符号+code
					obj_value = objects[1] + valueflagvalue + objects[0];
				if (valueflag.trim().toLowerCase().equals("1"))// 返回的codedesc=code+符号+codedesc
					obj_value = objects[0] + valueflagvalue + objects[1];
			}
			jsonObject.element("codedesc", obj_value);
			jsonArray.element(jsonObject);
		}
		
		setResultArray(jsonArray);
		return SUCCESS;
	}
	
	public String getUserRole(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String valueflag = request.getParameter("valueflag");
		String valueflagvalue = request.getParameter("valueflagvalue");
		String menuName=request.getParameter("tableName");
		String menuId=null;
		if(menuName!=null && menuName.trim().length()>0)
		{
			menuId=getMenuid(menuName);
		}
		
		JSONArray jsonArray = new JSONArray();
//		if("Eaoperaterole".equalsIgnoreCase(menuName)){
			UserInfoVo userInfoVo = WebUtils.getSessionContainer(request).getUserInfo();
			String acc=null;
			if(userInfoVo.getAccount().contains("BEAICITM")){//BEAICITM 技术管理员
				acc ="BEAICITM";
			}
			else if(userInfoVo.getAccount().contains("BEAICBMM")){//BEAICBMM 业务管理员
				acc ="BEAICBMM";
			}
			if(acc!=null){
				List list = commonService.getRoleUserByAccount(acc);
				if(list!=null && list.size()>0){
					for (Object o : list) {
						Object[] objects = (Object[]) o;
						Object account=objects[0];
						Object username=objects[1];
						JSONObject jsonObject = new JSONObject();
						jsonObject.element("code", objects[0]);
						Object obj_value = objects[1];
						if (valueflag != null && valueflag.trim().length() > 0) {
							if (valueflag.trim().toLowerCase().equals("0"))// 返回的codedesc=codedesc+符号+code
								obj_value = objects[1] + valueflagvalue + objects[0];
							if (valueflag.trim().toLowerCase().equals("1"))// 返回的codedesc=code+符号+codedesc
								obj_value = objects[0] + valueflagvalue + objects[1];
						}
						jsonObject.element("codedesc", obj_value);
						jsonArray.element(jsonObject);
					}
					setResultArray(jsonArray);
					return SUCCESS;
				}
			}
//		}
		
		
		System.out.println("menuId=="+menuId);
		List list = commonService.getRoleUser(menuId);
		jsonArray = new JSONArray();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			Object account=objects[0];
			Object username=objects[1];
			JSONObject jsonObject = new JSONObject();
//			System.out.println("account=="+account+"     username=="+username);
			jsonObject.element("code", objects[0]);
			Object obj_value = objects[1];
			if (valueflag != null && valueflag.trim().length() > 0) {
				if (valueflag.trim().toLowerCase().equals("0"))// 返回的codedesc=codedesc+符号+code
					obj_value = objects[1] + valueflagvalue + objects[0];
				if (valueflag.trim().toLowerCase().equals("1"))// 返回的codedesc=code+符号+codedesc
					obj_value = objects[0] + valueflagvalue + objects[1];
			}
			jsonObject.element("codedesc", obj_value);
			jsonArray.element(jsonObject);
		}
		setResultArray(jsonArray);
		return SUCCESS;
	}
	
	public String getMenuid(String menuName){
		List menuLst = commonService.getForeignLinks("eamenu", null, "menuid", "request", null,null, null);
		if(menuLst!=null && menuLst.size()>0){
			for (Object o : menuLst) {
				Object[] objects = (Object[]) o;
				String menuid=objects[0]+"";
				String request=objects[1]+"";
				if (request != null && request.lastIndexOf(".jsp") != -1) {
					String tableName = request.substring(request
							.lastIndexOf("/") + 1, request.lastIndexOf(".jsp"));
					if (menuName.equalsIgnoreCase(tableName)) {
						return menuid;
					}
				}
				else if (request != null && request.lastIndexOf("reqCode=") != -1) {
					String tableName = request.substring(request
							.lastIndexOf("reqCode=") + 8, request.length());
					 if("menuResourceInit".equals(tableName)){
						 tableName="Eamenu";
					 }
					 else if("roleInit".equals(tableName)){
						 tableName="Cmrole";
					 }
					 else if("userInit".equals(tableName)){
						 tableName="Eauser";
					 }
					 else if("departmentInit".equals(tableName)){
						 tableName="Eadept";
					 }
					if (menuName.equalsIgnoreCase(tableName)) {
						return menuid;
					}
				
				}
			}
		}
		return null;
	}

}
