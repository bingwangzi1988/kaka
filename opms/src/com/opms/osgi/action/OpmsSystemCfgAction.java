package com.opms.osgi.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.rif.util.WebUtils;

import com.common.base.BaseAction;
import com.common.util.CountOrder;
import com.common.util.CountOrderUtil;
import com.common.util.QueryUitl;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opms.osgi.po.OpmsSystemCfg;
import com.opms.osgi.service.OpmsSystemCfgService;
import com.opms.osgi.vo.OpmsSystemCfgQuery;


public class  OpmsSystemCfgAction extends BaseAction implements Preparable,ModelDriven{

     OpmsSystemCfgService  opmsSystemCfgService = (OpmsSystemCfgService) SpringBeanLoader.getSpringBean("opmsSystemCfgService");

    private JSON resultJson;

    private OpmsSystemCfg opmsSystemCfg;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         opmsSystemCfg = new OpmsSystemCfg();
    }

    public Object getModel() {
        return opmsSystemCfg;
    }

    public String list() {
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        OpmsSystemCfgQuery opmsSystemCfgQuery = QueryUitl.newQuery(OpmsSystemCfgQuery.class);
        List<OpmsSystemCfg> list = opmsSystemCfgService.searchOpmsSystemCfg(opmsSystemCfgQuery, countOrder);
        Long pageCount = opmsSystemCfgService.countOpmsSystemCfg(opmsSystemCfgQuery);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }
    
	public String listSystemId() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String code = request.getParameter("code");
        List list = WebUtils.getCodeListByField(code, request);
        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,list.size(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id =  new String(request.getParameter("id"));
        OpmsSystemCfg opmsSystemCfg = opmsSystemCfgService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(opmsSystemCfg ,null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject returnObject = new JSONObject();
        try
        {
           if(isEmpty(opmsSystemCfg.getId()))
            opmsSystemCfg.setId(null);
            String unique0 = opmsSystemCfgService.isUnique(opmsSystemCfg, "hostIp,hostPort", "主机IP,主机端口");
            if (unique0 != null) {
                returnObject.put("SUCCESS", false);
                returnObject.put("msg", unique0);
                setResultJson(returnObject);
                return SUCCESS;
            }
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                opmsSystemCfgService.update(opmsSystemCfg);
            else
                opmsSystemCfgService.save(opmsSystemCfg);
            returnObject.element(SUCCESS, true);
            returnObject.element("msg", "操作成功");

        }
        catch (Exception e)
        {
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
        try
        {
            for (int i = 0; i < idarray.length; i++)
            {
                String id = new String(idarray[i]);
                opmsSystemCfgService.removeById(id);
            }
            returnObject.put("success", true);
            returnObject.put("msg", "删除成功");
        }
        catch (Exception e)
        {
            returnObject.put("failure", true);
            returnObject.put("msg", e.getMessage());
            e.printStackTrace();
        }
        setResultJson(returnObject);
        return SUCCESS;
    }
}
