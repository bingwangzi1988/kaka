package com.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.arm.service.OrganizationService;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.util.WebUtils;

import com.common.base.BaseAction;
import com.opms.conmg.service.EaoperatercdService;

public class AuthorizationAction extends BaseAction {
	private static final long serialVersionUID = -7022734572341096727L;
	private static Log log = LogFactory.getLog(BaseAction.class);
	EaoperatercdService eaoperatercdService = (EaoperatercdService) SpringBeanLoader
			.getSpringBean("eaoperatercdService");
	private OrganizationService organizationService = (OrganizationService) super
			.getService("organizationService");

	private JSON resultJson;

	public JSON getResultJson() {
		return resultJson;
	}

	public void setResultJson(JSON resultJson) {
		this.resultJson = resultJson;
	}

	public String executeAuthority() {
		JSONObject jsonObject = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		String authoriseId = request.getParameter("authoriseId");// 授权员工号
		String authorisePwd = request.getParameter("authorisePwd");// 授权密码
		String operateType = request.getParameter("operateType");// 操作类型
		String operateObject = request.getParameter("operateObject");// 操作对象
		if (authorisePwd != null && authorisePwd.trim().length() > 0)
			authorisePwd = G4Utils.encryptBasedDes(authorisePwd);

		UserInfoVo userInfoVo = WebUtils.getSessionContainer(request)
				.getUserInfo();
		String userId = userInfoVo.getAccount();// 当前操作员员工号
		if (userId.equals(authoriseId)) {
			jsonObject.put("failure", true);
			jsonObject.put("msg", "授权失败：不能本人授权，请选择其他员工号授权！");
			setResultJson(jsonObject);
			return SUCCESS;
		}

		if (authoriseId != null && authoriseId.trim().length() > 0) {
			Dto dto = new BaseDto();
			dto.put("account", authoriseId);
			Dto outDto = organizationService.getUserInfo(dto);
			UserInfoVo userInfo = (UserInfoVo) outDto.get("userInfo");

			if (userInfo == null) {
				jsonObject.put("failure", true);
				jsonObject.put("msg", "授权失败：授权员工不存在！");
				setResultJson(jsonObject);
				return SUCCESS;
			} else {
				if(userInfo.getChgPwdDate()==null){
					jsonObject.put("failure", true);
					jsonObject.put("msg", "授权失败：授权员工初始密码未修改！");
					setResultJson(jsonObject);
					return SUCCESS;
				}
				Dto pto = new BaseDto();
				pto.put("userid", userInfo.getUserid());
				List roleList = organizationService.queryUsersRoleByUserId(pto);
				if (roleList != null && roleList.size() > 0) {
					boolean isGrant=false;
					for (int i = 0; i < roleList.size(); i++) {
						Dto roledto = (BaseDto) roleList.get(i);
						List grantedList = organizationService
								.queryUsersMenusByRoleId(roledto);
						isGrant=checkGeant(grantedList, operateObject);
						if(isGrant){
							break;
						}
					}
					if (!isGrant) {
						jsonObject.put("failure", true);
						jsonObject.put("msg", "授权失败：该柜员没有授权权限！");
						setResultJson(jsonObject);
						return SUCCESS;
					}
				} else {
					jsonObject.put("failure", true);
					jsonObject.put("msg", "授权失败：该柜员没有授权权限！");
					setResultJson(jsonObject);
					return SUCCESS;
				}

				/**
				 * 单个人员授权
				 * 
				 * start
				 * */

				// List grantedList =
				// organizationService.queryUsersMenusByUserId(pto);//
				// 通过userid查询 Eauserrole 表
				// 通过menuid 查询eamenu表 截取request 如：jsp/cardkind/ticbasefee.jsp

				// if (!checkGeant(grantedList, operateObject)) {
				// jsonObject.put("failure", true);
				// jsonObject.put("msg", "授权失败：该柜员没有授权权限！");
				// setResultJson(jsonObject);
				// return SUCCESS;
				// }
				/**
				 * end
				 * */

				if (!authorisePwd.equals(userInfo.getPassword())) {
					jsonObject.put("failure", true);
					jsonObject.put("msg", "授权失败：授权密码输入错误,请重新输入！");
					setResultJson(jsonObject);
					return SUCCESS;
				}

				// String deptid = userInfoVo.getDeptid();// 当前操作员部门编号
				// String authdeptid = userInfo.getDeptid();// 授权员工部门编号

				// Dto deptdto = new BaseDto();
				// deptdto.put("deptid", deptid);
				// Dto deptDto = organizationService
				// .queryDeptinfoByDeptid(deptdto);
				// String parentid = (String) deptDto.get("parentid");
				// if (!parentid.equals(authdeptid)) {
				// jsonObject.put("failure", true);
				// jsonObject.put("msg", "授权失败：该柜员没有授权权限！");
				// setResultJson(jsonObject);
				// return SUCCESS;
				// }
			}
		}

		// 测试 start
		Map<String, Object> map = new HashMap<String, Object>();
		String returnStatus = "SUCCESS";
		String faultString = "成功";
		// 测试 end

		log.info("授权验密返回信息：" + map);
		if (returnStatus.equals("SUCCESS")) {
			JSONObject operateParams = new JSONObject();
			operateParams.put("userId", userId);
			operateParams.put("authoriseId", authoriseId);
			operateParams
					.put("operateType", getType(operateType.toUpperCase()));
			operateParams.put("operateObject", operateObject);
			System.out.println(operateParams.toString());
			jsonObject.put("success", true);
			jsonObject.put("msg", operateParams);
		} else {
			jsonObject.put("failure", true);
			jsonObject.put("msg", "授权失败");
		}
		setResultJson(jsonObject);
		return SUCCESS;
	}

	private Boolean checkGeant(List grantList, String menuName) {
		Boolean result = new Boolean(false);
		for (int i = 0; i < grantList.size(); i++) {
			Dto dto = (BaseDto) grantList.get(i);
			String menuid = dto.getAsString("menuid");
			Dto pto = new BaseDto();
			pto.put("menuid", menuid);
			Dto outDto = organizationService.getMenuInfoByMenuid(pto);
			if (outDto != null) {
				String request = (String) outDto.get("request");
				if (request != null && request.lastIndexOf(".jsp") != -1) {
					String tableName = request.substring(request
							.lastIndexOf("/") + 1, request.lastIndexOf(".jsp"));
					// System.out.println("tableName=="+tableName+"  menuName=="+menuName);
					if (menuName.equalsIgnoreCase(tableName)) {
						result = new Boolean(true);
						break;
					}
				}
				else if (request != null && request.lastIndexOf("reqCode=") != -1) {

					String tableName = request.substring(request
							.lastIndexOf("reqCode=") + 8, request.length());
//					 System.out.println("tableName=="+tableName+"  menuName=="+menuName);
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
						result = new Boolean(true);
						break;
					}
				
				}
			}
		}
//		 System.out.println("result=="+result);
		return result;
	}

	private String getType(String upperCase) {
		String returnType = upperCase;
		if (upperCase.startsWith("SAVE")) {
			returnType = "SAVE";
		} else if (upperCase.startsWith("UPDATE")) {
			returnType = "UPDATE";
		} else if (upperCase.startsWith("DELETE")) {
			returnType = "DELETE";
		} else {
			returnType = upperCase;
		}
		return returnType;
	}

}
