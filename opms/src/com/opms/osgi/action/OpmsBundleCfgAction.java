package com.opms.osgi.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

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
import com.opms.osgi.po.OpmsBundleCfg;
import com.opms.osgi.service.OpmsBundleCfgService;
import com.opms.osgi.vo.OpmsBundleCfgQuery;


public class  OpmsBundleCfgAction extends BaseAction implements Preparable,ModelDriven{

     OpmsBundleCfgService  opmsBundleCfgService = (OpmsBundleCfgService) SpringBeanLoader.getSpringBean("opmsBundleCfgService");

    private JSON resultJson;

    private OpmsBundleCfg opmsBundleCfg;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         opmsBundleCfg = new OpmsBundleCfg();
    }

    public Object getModel() {
        return opmsBundleCfg;
    }

    public String list() {
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        OpmsBundleCfgQuery opmsBundleCfgQuery = QueryUitl.newQuery(OpmsBundleCfgQuery.class);
        List<OpmsBundleCfg> list = opmsBundleCfgService.searchOpmsBundleCfg(opmsBundleCfgQuery, countOrder);
        Long pageCount = opmsBundleCfgService.countOpmsBundleCfg(opmsBundleCfgQuery);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id =  new String(request.getParameter("id"));
        OpmsBundleCfg opmsBundleCfg = opmsBundleCfgService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(opmsBundleCfg ,null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject returnObject = new JSONObject();
        try
        {
           if(isEmpty(opmsBundleCfg.getId()))
            opmsBundleCfg.setId(null);
            String unique0 = opmsBundleCfgService.isUnique(opmsBundleCfg, "systemId,bundleName", "系统标识,组件名");
            if (unique0 != null) {
                returnObject.put("SUCCESS", false);
                returnObject.put("msg", unique0);
                setResultJson(returnObject);
                return SUCCESS;
            }
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                opmsBundleCfgService.update(opmsBundleCfg);
            else
                opmsBundleCfgService.save(opmsBundleCfg);
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
                opmsBundleCfgService.removeById(id);
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
