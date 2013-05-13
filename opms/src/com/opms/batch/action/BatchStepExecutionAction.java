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
import com.opms.batch.po.BatchStepExecution;
import com.opms.batch.service.BatchStepExecutionService;
import com.opms.batch.vo.BatchStepExecutionQuery;


public class  BatchStepExecutionAction extends BaseAction implements Preparable,ModelDriven{

     BatchStepExecutionService  batchStepExecutionService = (BatchStepExecutionService) SpringBeanLoader.getSpringBean("batchStepExecutionService");

    private JSON resultJson;

    private BatchStepExecution batchStepExecution;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         batchStepExecution = new BatchStepExecution();
    }

    public Object getModel() {
        return batchStepExecution;
    }

    public String list() {
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        BatchStepExecutionQuery batchStepExecutionQuery = QueryUitl.newQuery(BatchStepExecutionQuery.class);
        List<BatchStepExecution> list = batchStepExecutionService.searchBatchStepExecution(batchStepExecutionQuery, countOrder);
        Long pageCount = batchStepExecutionService.countBatchStepExecution(batchStepExecutionQuery);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        BigDecimal id =  new BigDecimal(request.getParameter("id"));
        BatchStepExecution batchStepExecution = batchStepExecutionService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(batchStepExecution ,null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject returnObject = new JSONObject();
        try
        {
           if(isEmpty(batchStepExecution.getId()))
            batchStepExecution.setId(null);
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                batchStepExecutionService.update(batchStepExecution);
            else
                batchStepExecutionService.save(batchStepExecution);
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
                batchStepExecutionService.removeById(id);
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
