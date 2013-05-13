package com.opms.batch.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.neptune.batch.core.invoker.ContextService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;

import com.common.base.BaseAction;
import com.common.util.CountOrder;
import com.common.util.CountOrderUtil;
import com.common.util.PorpertiesUtil;
import com.common.util.QueryUitl;
import com.common.util.WebserviceClient;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opms.batch.po.BatchStep;
import com.opms.batch.service.BatchStepService;
import com.opms.batch.vo.BatchStepQuery;


public class  BatchStepAction extends BaseAction implements Preparable,ModelDriven{

     BatchStepService  batchStepService = (BatchStepService) SpringBeanLoader.getSpringBean("batchStepService");

    private JSON resultJson;

    private BatchStep batchStep;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         batchStep = new BatchStep();
    }

    public Object getModel() {
        return batchStep;
    }

    public String list() {
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        BatchStepQuery batchStepQuery = QueryUitl.newQuery(BatchStepQuery.class);
        List<BatchStep> list = batchStepService.searchBatchStep(batchStepQuery, countOrder);
        Long pageCount = batchStepService.countBatchStep(batchStepQuery);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id =  new String(request.getParameter("id"));
        BatchStep batchStep = batchStepService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(batchStep ,null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject returnObject = new JSONObject();
        try
        {
        	Properties props = PorpertiesUtil.getProperties("socket.properties");
        	ContextService contextService = WebserviceClient.getServiceClient(props.getProperty("BatchHostIp"), props.getProperty("BatchHostPort"), "ContextService", ContextService.class);
        	contextService.reloadJobGrpCfg();
        	if(isEmpty(batchStep.getId())) {
        		batchStep.setId(null);
        	}
            String unique0 = batchStepService.isUnique(batchStep, "jobId,priority", ",");
            if (unique0 != null) {
                returnObject.put("SUCCESS", false);
                returnObject.put("msg", unique0);
                setResultJson(returnObject);
                return SUCCESS;
            }
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                batchStepService.update(batchStep);
            else
                batchStepService.save(batchStep);
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
        	Properties props = PorpertiesUtil.getProperties("socket.properties");
        	ContextService contextService = WebserviceClient.getServiceClient(props.getProperty("BatchHostIp"), props.getProperty("BatchHostPort"), "ContextService", ContextService.class);
        	contextService.reloadJobGrpCfg();
            for (int i = 0; i < idarray.length; i++)
            {
                String id = new String(idarray[i]);
                batchStepService.removeById(id);
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
