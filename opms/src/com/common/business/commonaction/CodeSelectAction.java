package com.common.business.commonaction;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.rif.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-14
 * Time: ÏÂÎç3:07
 * desc:
 */
public class CodeSelectAction extends ActionSupport {

    private JSONArray resultArray;

    public JSONArray getResultArray() {
        return resultArray;
    }

    public void setResultArray(JSONArray resultArray) {
        this.resultArray = resultArray;
    }

    public String execute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String code = request.getParameter("code");
        List list = WebUtils.getCodeListByField(code, request);
        JSONArray jsonArray = JSONArray.fromObject(list);
        setResultArray(jsonArray);
        return SUCCESS;
    }
}
