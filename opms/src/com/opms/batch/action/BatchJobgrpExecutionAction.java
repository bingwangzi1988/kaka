package com.opms.batch.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.neptune.batch.core.invoker.BatchExecutionService;
import net.neptune.batch.core.invoker.BatchInvoker;
import net.neptune.batch.core.invoker.ContextService;
import net.neptune.batch.dao.batchjobexecution.BatchJobExecution;
import net.neptune.batch.dao.batchstepexecution.BatchStepExecution;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.base.BaseAction;
import com.common.util.DateUtil;
import com.common.util.PorpertiesUtil;
import com.common.util.Utils;
import com.common.util.WebserviceClient;
import com.common.util.XmlParseUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opms.batch.model.BatchHostCfg;
import com.opms.batch.model.Host;
import com.opms.batch.model.HostCfg;
import com.opms.batch.po.BatchJobgrpExecution;
import com.opms.batch.service.BatchJobExecutionService;
import com.opms.batch.service.BatchJobgrpExecutionService;
import com.opms.batch.service.BatchStepExecutionService;


public class  BatchJobgrpExecutionAction extends BaseAction implements Preparable,ModelDriven{

	private static Logger log = LoggerFactory.getLogger(BatchJobgrpExecutionAction.class);
	
    BatchJobgrpExecutionService  batchJobgrpExecutionService = (BatchJobgrpExecutionService) SpringBeanLoader.getSpringBean("batchJobgrpExecutionService");
    BatchJobExecutionService  batchJobExecutionService = (BatchJobExecutionService) SpringBeanLoader.getSpringBean("batchJobExecutionService");
    BatchStepExecutionService  batchStepExecutionService = (BatchStepExecutionService) SpringBeanLoader.getSpringBean("batchStepExecutionService");

    private JSON resultJson;

    private BatchJobgrpExecution batchJobgrpExecution;

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         batchJobgrpExecution = new BatchJobgrpExecution();
    }

    public Object getModel() {
        return batchJobgrpExecution;
    }

    public String list() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String jobGroupName = request.getParameter("jobGroupName");
		String batchNo = request.getParameter("batchNo");
		String startTimeBegin = request.getParameter("startTimeBegin");
		Date beginDate = null;
		Date endDate = null;
		if(startTimeBegin != null && !"".equals(startTimeBegin)) {
			try {
				beginDate = DateUtil.parseStrToDate(startTimeBegin, "yyyy-MM-dd");
				endDate = DateUtil.getAddDays(beginDate, 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String status = request.getParameter("status");
		int start = 0;
		int limit = 0;
		String startStr = request.getParameter("start");
		if(startStr != null && !"".equals(startStr)) {
			start = Integer.parseInt(startStr);
		}
		String limitStr = request.getParameter("limit");
		if(limitStr != null && !"".equals(limitStr)) {
			limit = Integer.parseInt(limitStr);
		}
		
		Properties props = PorpertiesUtil.getProperties("socket.properties");
		BatchExecutionService batchExecutionService = WebserviceClient.getServiceClient(props.getProperty("BatchHostIp"), props.getProperty("BatchHostPort"), "BatchExecutionService", BatchExecutionService.class);
		ArrayList<net.neptune.batch.dao.batchjobgrpexecution.BatchJobgrpExecution> batchJobgrpExecutions = batchExecutionService.findBatchJobgrpExecutionList(jobGroupName, batchNo, beginDate, endDate, status, start, limit);
		Long pageCount = batchExecutionService.countBatchJobgrpExecution(jobGroupName, batchNo, beginDate, endDate, status);
       
		JSONObject jsonObject = JsonHelper.encodeList2JSONArray(batchJobgrpExecutions, pageCount.intValue(), "yyyy-MM-dd HH:mm:ss");
        setResultJson(jsonObject);
        return SUCCESS;
    }
    
    public String listJobgrp() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	
    	String nodeid = request.getParameter("node");
    	
    	Properties props = PorpertiesUtil.getProperties("socket.properties");
    	BatchExecutionService batchExecutionService = WebserviceClient.getServiceClient(props.getProperty("BatchHostIp"), props.getProperty("BatchHostPort"), "BatchExecutionService", BatchExecutionService.class);
    	String json = batchExecutionService.getJobgrpJson(nodeid);
    	System.out.println(json);
    	Utils.PrintWrite(response, json);
    	return SUCCESS;
    }

    public String list2() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String nodeid = request.getParameter("node");
    	StringBuffer strBuf = new StringBuffer("[");
    	String json = "";
    	Properties props = PorpertiesUtil.getProperties("socket.properties");
    	BatchExecutionService batchExecutionService = WebserviceClient.getServiceClient(props.getProperty("BatchHostIp"), props.getProperty("BatchHostPort"), "BatchExecutionService", BatchExecutionService.class);
    	if("001".equals(nodeid)) {
    		ArrayList<net.neptune.batch.dao.batchjobgrpexecution.BatchJobgrpExecution> batchJobgrpExecutions = batchExecutionService.searchBatchJobgrpExecution();
    		if(batchJobgrpExecutions != null) {
	    		for(net.neptune.batch.dao.batchjobgrpexecution.BatchJobgrpExecution batchJobgrpExecution : batchJobgrpExecutions) {
	    			strBuf.append("{")
	    				  .append("'id':'").append(batchJobgrpExecution.getId())
	    				  .append("','name':'").append(batchJobgrpExecution.getName())
	    				  .append("','batchNo':'").append(batchJobgrpExecution.getBatchNo())
	    				  .append("','ipAddr':'").append(batchJobgrpExecution.getIpAddr())
	    				  .append("','starttime':'").append(DateUtil.formatDateToStr(batchJobgrpExecution.getStartTime(), "yyyy-MM-dd HH:mm:ss"))
	    				  .append("','endtime':'").append(DateUtil.formatDateToStr(batchJobgrpExecution.getEndTime(), "yyyy-MM-dd HH:mm:ss"))
	    				  .append("','status':'").append(batchJobgrpExecution.getStatus())
	    				  .append("','errMsg':''")
	    				  .append(",'parentid':'001'")
	    				  .append(", 'type': '1")
	    				  .append("','leaf':false")
	    				  .append(",'expanded':false")
	    				  .append("},");
	    		}
	    		json = strBuf.substring(0, strBuf.length()-1);
    		}
    	} else {
    		ArrayList<BatchJobExecution> batchJobExecutions = batchExecutionService.searchBatchJobExecution(nodeid);
    		if(batchJobExecutions != null) {
	    		for(BatchJobExecution batchJobExecution : batchJobExecutions) {
	    			String errMsg = batchJobExecution.getErrMsg()==null?"":batchJobExecution.getErrMsg();
	    			strBuf.append("{")
				  .append("'id':'").append(batchJobExecution.getId())
				  .append("','name':'").append(batchJobExecution.getName())
				  .append("','batchNo':'")
				  .append("','ipAddr':'").append(batchJobExecution.getIpAddr())
				  .append("','starttime':'").append(DateUtil.formatDateToStr(batchJobExecution.getStartTime(), "yyyy-MM-dd HH:mm:ss"))
				  .append("','endtime':'").append(DateUtil.formatDateToStr(batchJobExecution.getEndTime(), "yyyy-MM-dd HH:mm:ss"))
				  .append("','status':'").append(batchJobExecution.getStatus())
				  .append("','errMsg':'").append(errMsg)
				  .append("','parentid':'").append(nodeid)
				  .append("', 'type': '2")
				  .append("','leaf':false")
				  .append(",'expanded':false")
				  .append("},");
	    		}
    		}
    		List<BatchStepExecution> batchStepExecutions = batchExecutionService.searchBatchStepExecution(nodeid);
    		if(batchStepExecutions != null) {
	    		for(BatchStepExecution batchStepExecution : batchStepExecutions) {
	    			String errMsg = batchStepExecution.getErrMsg()==null?"":batchStepExecution.getErrMsg();
	    			strBuf.append("{")
				  .append("'id':'").append(batchStepExecution.getId())
				  .append("','name':'").append(batchStepExecution.getName())
				  .append("','batchNo':'")
				  .append("','ipAddr':'")
				  .append("','starttime':'").append(DateUtil.formatDateToStr(batchStepExecution.getStartTime(), "yyyy-MM-dd HH:mm:ss"))
				  .append("','endtime':'").append(DateUtil.formatDateToStr(batchStepExecution.getEndTime(), "yyyy-MM-dd HH:mm:ss"))
				  .append("','status':'").append(batchStepExecution.getStatus())
				  .append("','errMsg':'").append(errMsg)
				  .append("','parentid':'").append(nodeid)
				  .append("', 'type': '3")
				  .append("','leaf':true")
				  .append(",'expanded':false")
				  .append("},");
	    		}
    		}
    		json = strBuf.substring(0, strBuf.length()-1);
    	}
    	json = json+"]";
    	
//    	String json = "[{'id':'001003','name':2,'batchNo':'交易产品研发部','ipAddr':null,'starttime':'001','endtime':false,'status':true,'errMsg':'','leaf':true,'expanded':false},{'id':'001004','name':2,'batchNo':'交易产品研发部','ipAddr':null,'starttime':'001','endtime':false,'status':true,'errMsg':'','leaf':true,'expanded':false}]";
    	Utils.PrintWrite(response, json);
    	return SUCCESS;
    }
    
    public String list3() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String menuid = request.getParameter("menuid");
    	String menuname = request.getParameter("menuname");
    	String menutype = request.getParameter("menutype");
    	if(menuid == null || "00".equals(menuid)) {
    		menuname = null;
    		menutype = "1";
    	}
    	Properties props = PorpertiesUtil.getProperties("socket.properties");
    	BatchExecutionService batchExecutionService = WebserviceClient.getServiceClient(props.getProperty("BatchHostIp"), props.getProperty("BatchHostPort"), "BatchExecutionService", BatchExecutionService.class);
    	String json = batchExecutionService.getBatchJobgrpExecution(menuname, menutype);
//    	String json = "{TOTALCOUNT:2,ROOT:[{'name':'name','id':'1','batchNo':'2','ipAddr':'192.168.1.1','starttime':'starttime','endtime':'endtime','status':'S'},{'name':'name','id':'2','batchNo':'2','ipAddr':'192.168.1.1','starttime':'starttime','endtime':'endtime','status':'S'}]}";
//    	String json = "{TOTALCOUNT:2,ROOT:[{'name':'DayEnd'id':'4903','batchNo':'13051000002159','ipAddr':'192.168.1.2','starttime':'2013-05-10 10:23:49','endtime':'2013-05-10 10:23:49','status':'F','errmsg':''},{'name':'DayEnd'id':'4921','batchNo':'13051000002159','ipAddr':'192.168.1.2','starttime':'2013-05-10 10:29:17','endtime':'2013-05-10 10:29:17','status':'S','errmsg':''}]}";
    	System.out.println(json);
    	Utils.PrintWrite(response, json);
    	return SUCCESS;
    }
    
    public String continueRun() {
    	HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject jsonObject = new JSONObject();
        try {
	        String host = request.getParameter("hostIp");
	        String batchNo = request.getParameter("batNo");
	        String[] hostArr = host.split("\\|");
	        String batchHostIp = hostArr[0];
	        String batchHostPort = hostArr[1];
	        
	        BatchInvoker batchInvokerService = WebserviceClient.getServiceClient(batchHostIp, batchHostPort, "BatchInvoker", BatchInvoker.class);
	        batchInvokerService.invokeContinue(batchNo);
	        jsonObject.element(SUCCESS, true);
	        jsonObject.element("msg", "续跑调起成功");
        } catch(Exception e) {
        	jsonObject.put("failure", true);
        	jsonObject.put("msg", e.getMessage());
        }
        setResultJson(jsonObject);
        return SUCCESS;
    }
    
    public String listHost() {
    	JSONArray jsonArray = new JSONArray();
    	try {
    		HostCfg hostCfg = XmlParseUtil.parseXML(HostCfg.class, ResourcesUtil.getResourceAsFile("host.xml"));
    		BatchHostCfg batchHostCfg = hostCfg.getBatchHostCfg();
    		if(batchHostCfg != null) {
				for (Host host : batchHostCfg.getHosts()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.element("code", host.getIp() + "|" + host.getPort());
					jsonObject.element("codedesc", host.getIp());
					jsonArray.element(jsonObject);
				}
    		}
    	} catch (Exception e) {
    		log.error("", e);
		}
		setResultJson(jsonArray);
        return SUCCESS;
    }
    
    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        BigDecimal id =  new BigDecimal(request.getParameter("id"));
        BatchJobgrpExecution batchJobgrpExecution = batchJobgrpExecutionService.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(batchJobgrpExecution ,null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject returnObject = new JSONObject();
        try
        {
           if(isEmpty(batchJobgrpExecution.getId()))
            batchJobgrpExecution.setId(null);
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                batchJobgrpExecutionService.update(batchJobgrpExecution);
            else
                batchJobgrpExecutionService.save(batchJobgrpExecution);
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
                batchJobgrpExecutionService.removeById(id);
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
