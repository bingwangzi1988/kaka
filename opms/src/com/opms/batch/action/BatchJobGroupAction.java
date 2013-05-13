package com.opms.batch.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.neptune.batch.core.invoker.ContextService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.base.BaseAction;
import com.common.util.CountOrder;
import com.common.util.CountOrderUtil;
import com.common.util.PorpertiesUtil;
import com.common.util.QueryUitl;
import com.common.util.WebserviceClient;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opms.batch.po.BatchJobGroup;
import com.opms.batch.service.BatchJobGroupService;
import com.opms.batch.vo.BatchJobGroupQuery;

public class  BatchJobGroupAction extends BaseAction implements Preparable,ModelDriven{

	private static final long serialVersionUID = 47972780782092629L;

	private static Logger log = LoggerFactory.getLogger(BatchJobGroupAction.class);
	BatchJobGroupService  batchJobGroupService = (BatchJobGroupService) SpringBeanLoader.getSpringBean("batchJobGroupService");

    private JSON resultJson;

    private BatchJobGroup batchJobGroup;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         batchJobGroup = new BatchJobGroup();
    }

    public Object getModel() {
        return batchJobGroup;
    }

    public String list() {
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        BatchJobGroupQuery batchJobGroupQuery = QueryUitl.newQuery(BatchJobGroupQuery.class);
        List<BatchJobGroup> list = batchJobGroupService.searchBatchJobGroup(batchJobGroupQuery, countOrder);
        Long pageCount = batchJobGroupService.countBatchJobGroup(batchJobGroupQuery);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }
    
    public String listClass() {
    	JSONArray jsonArray = new JSONArray();
    	try {
    		Properties props = PorpertiesUtil.getProperties("socket.properties");
    		ContextService contextService = WebserviceClient.getServiceClient(props.getProperty("BatchHostIp"), props.getProperty("BatchHostPort"), "ContextService", ContextService.class);
	    	ArrayList<String> stepClassList = contextService.getStepExecutors();
	        
			for (String stepClass : stepClassList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("code", stepClass);
				jsonObject.element("codedesc", stepClass);
				jsonArray.element(jsonObject);
			}
    	} catch (Exception e) {
    		log.error("获取StepExecutorClassService服务异常");
		}
		setResultJson(jsonArray);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id =  new String(request.getParameter("id"));
        BatchJobGroup batchJobGroup = batchJobGroupService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(batchJobGroup ,null);
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
        	if(isEmpty(batchJobGroup.getId())) {
        		batchJobGroup.setId(null);
        	}
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                batchJobGroupService.update(batchJobGroup);
            else
                batchJobGroupService.save(batchJobGroup);
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
                batchJobGroupService.removeById(id);
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
