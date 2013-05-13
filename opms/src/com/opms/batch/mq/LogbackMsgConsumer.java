package com.opms.batch.mq;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.LoggingEventVO;

public class LogbackMsgConsumer implements MessageListener {  
	
	final static Logger logger = LoggerFactory.getLogger(LogbackMsgConsumer.class);
	
	private static ThreadPoolExecutor threadPool;
	
	static {
		//  ÔÝ¶¨
		threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
	}
	
    public void onMessage(Message m) {  
        ObjectMessage om = (ObjectMessage) m;
        try {
			LoggingEventVO loggingEventVO = (LoggingEventVO) om.getObject();
			threadPool.execute(new LogbackMsgProcessor(loggingEventVO));
		} catch (JMSException e) {
			logger.error("", e);
		}
    }
    
} 
