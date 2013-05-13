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
import com.opms.batch.po.BatchJob;
import com.opms.batch.service.BatchJobService;
import com.opms.batch.vo.BatchJobQuery;


public class  BatchJobAction extends BaseAction implements Preparable,ModelDriven{

     BatchJobService  batchJobService = (BatchJobService) SpringBeanLoader.getSpringBean("batchJobService");

    private JSON resultJson;

    private BatchJob batchJob;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         batchJob = new BatchJob();
    }

    public Object getModel() {
        return batchJob;
    }

    public String list() {
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        BatchJobQuery batchJobQuery = QueryUitl.newQuery(BatchJobQuery.class);
        List<BatchJob> list = batchJobService.searchBatchJob(batchJobQuery, countOrder);
        Long pageCount = batchJobService.countBatchJob(batchJobQuery);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id =  new String(request.getParameter("id"));
        BatchJob batchJob = batchJobService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(batchJob ,null);
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
        	if(isEmpty(batchJob.getId())) {
        		batchJob.setId(null);
        	}
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                batchJobService.update(batchJob);
            else
                batchJobService.save(batchJob);
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
                batchJobService.removeById(id);
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
