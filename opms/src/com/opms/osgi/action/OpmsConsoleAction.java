package com.opms.osgi.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.common.base.BaseAction;
import com.common.util.TelnetUtils;
import com.common.util.Utils;


public class OpmsConsoleAction extends BaseAction {

	private static final long serialVersionUID = -3156553216538990692L;
	
	private static Log log = LogFactory.getLog(OpmsConsoleAction.class);
	
	private JSON resultJson;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public String list() {
        return SUCCESS;
    }
    
    public String doConsole() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	JSONObject jsonObject = new JSONObject();
    	String resInfo = null;

    	String hostIp = request.getParameter("hostIp");
    	String hostPort = request.getParameter("hostPort");
    	String cmd = request.getParameter("cmd");
    	log.info("cmd: " + cmd);
    	try {
    		if(cmd != null && !"".equals(cmd.trim())) {
				TelnetUtils telnetUtils = new TelnetUtils(hostIp, Integer.parseInt(hostPort), null, null);
				resInfo = telnetUtils.execCmd(cmd);
				log.info("ResInfo: " + resInfo);
				if(StringUtils.isBlank(resInfo)) {
					jsonObject.element("success", false);
					jsonObject.element("msg", "操作失败");
				} else {
					jsonObject.element("success", true);
					jsonObject.element("msg", "操作成功");
				}
			} else {
				jsonObject.element("success", false);
				jsonObject.element("msg", "操作失败");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			jsonObject.element("success", false);
			jsonObject.element("msg", "操作失败, 失败原因：" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.element("success", false);
			jsonObject.element("msg", "操作失败, 失败原因：" + e.getMessage());
		}
//		Utils.PrintWrite(response, jsonObject.toString());
        return SUCCESS;
    }

}
