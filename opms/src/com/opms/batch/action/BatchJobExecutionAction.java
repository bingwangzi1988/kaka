package com.opms.batch.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

import java.math.BigDecimal;
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
import com.opms.batch.po.BatchJobExecution;
import com.opms.batch.service.BatchJobExecutionService;
import com.opms.batch.vo.BatchJobExecutionQuery;


public class  BatchJobExecutionAction extends BaseAction implements Preparable,ModelDriven{

     BatchJobExecutionService  batchJobExecutionService = (BatchJobExecutionService) SpringBeanLoader.getSpringBean("batchJobExecutionService");

    private JSON resultJson;

    private BatchJobExecution batchJobExecution;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         batchJobExecution = new BatchJobExecution();
    }

    public Object getModel() {
        return batchJobExecution;
    }

    public String list() {
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        BatchJobExecutionQuery batchJobExecutionQuery = QueryUitl.newQuery(BatchJobExecutionQuery.class);
        List<BatchJobExecution> list = batchJobExecutionService.searchBatchJobExecution(batchJobExecutionQuery, countOrder);
        Long pageCount = batchJobExecutionService.countBatchJobExecution(batchJobExecutionQuery);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        BigDecimal id =  new BigDecimal(request.getParameter("id"));
        BatchJobExecution batchJobExecution = batchJobExecutionService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(batchJobExecution ,null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject returnObject = new JSONObject();
        try
        {
           if(isEmpty(batchJobExecution.getId()))
            batchJobExecution.setId(null);
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                batchJobExecutionService.update(batchJobExecution);
            else
                batchJobExecutionService.save(batchJobExecution);
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
                BigDecimal id = new BigDecimal(idarray[i]);
                batchJobExecutionService.removeById(id);
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
