package com.opms.conmg.action;

import com.common.base.BaseAction;
import com.common.util.CountOrder;
import com.common.util.CountOrderUtil;
import com.common.util.QueryUitl;
import com.opms.conmg.po.OpmsHostCfg;
import com.opms.conmg.service.OpmsHostCfgService;
import com.opms.conmg.vo.OpmsHostCfgQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;


public class  OpmsHostCfgAction extends BaseAction implements Preparable,ModelDriven{

     OpmsHostCfgService  opmsHostCfgService = (OpmsHostCfgService) SpringBeanLoader.getSpringBean("opmsHostCfgService");

    private JSON resultJson;

    private OpmsHostCfg opmsHostCfg;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         opmsHostCfg = new OpmsHostCfg();
    }

    public Object getModel() {
        return opmsHostCfg;
    }

    public String list() {
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        OpmsHostCfgQuery opmsHostCfgQuery = QueryUitl.newQuery(OpmsHostCfgQuery.class);
        List<OpmsHostCfg> list = opmsHostCfgService.searchOpmsHostCfg(opmsHostCfgQuery, countOrder);
        Long pageCount = opmsHostCfgService.countOpmsHostCfg(opmsHostCfgQuery);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id =  new String(request.getParameter("id"));
        OpmsHostCfg opmsHostCfg = opmsHostCfgService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(opmsHostCfg ,null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject returnObject = new JSONObject();
        try
        {
           if(isEmpty(opmsHostCfg.getId()))
            opmsHostCfg.setId(null);
            String unique0 = opmsHostCfgService.isUnique(opmsHostCfg, "hostIp,hostPort", "主机IP,主机端口");
            if (unique0 != null) {
                returnObject.put("SUCCESS", false);
                returnObject.put("msg", unique0);
                setResultJson(returnObject);
                return SUCCESS;
            }
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                opmsHostCfgService.update(opmsHostCfg);
            else
                opmsHostCfgService.save(opmsHostCfg);
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
                opmsHostCfgService.removeById(id);
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
