package com.opms.batch.mq;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.LoggingEventVO;

import com.common.util.DateUtil;
import com.opms.batch.dwr.BatchMsg;
import com.opms.batch.dwr.BatchService;

public class LogbackMsgProcessor implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(LogbackMsgProcessor.class);
	private LoggingEventVO loggingEventVO;
	
	public LogbackMsgProcessor(LoggingEventVO loggingEventVO) {
		super();
		this.loggingEventVO = loggingEventVO;
	}

	public void run() {
		try {  
        	Date date = new Date(loggingEventVO.getTimeStamp());
//        	System.out.println(date);
//        	System.out.println(loggingEventVO.getLevel().levelStr);
//        	System.out.println(loggingEventVO.getThreadName());
//        	System.out.println(((LoggingEventVO)om.getObject()).getMessage());
//        	Object obj = om.getObject();
//        	MonitorInfo info = new MonitorInfo();
//        	info.setState(Constants.JOBGRPSTARTEVENT);
//        	info.setMsg(DateUtil.formatDateToStr(date, "yyyy-MM-dd HH:mm:ss") + " " + loggingEventVO.getMdc().get("BatchJobGroup") + " " + loggingEventVO.getLevel().levelStr + " " + loggingEventVO.getLoggerName() + " " + loggingEventVO.getMessage() + "\n");
//        	TaskMonitor.publishLog(info);
        	
        	String message = loggingEventVO.getMessage();
        	
        	
        	BatchMsg batchMsg = new BatchMsg();
    		batchMsg.setMsg(DateUtil.formatDateToStr(date, "yyyy-MM-dd HH:mm:ss") + " " + loggingEventVO.getMdc().get("BatchJobGroup") + " " + loggingEventVO.getLevel().levelStr + " " + loggingEventVO.getLoggerName() + " " + loggingEventVO.getMessage() + "\n");
    		batchMsg.setTime(new Date());
    		BatchService.sendMessage(batchMsg);
        } catch (Exception e) {  
        	logger.error("", e); 
        }  
	}

}