package com.opms.batch.dwr;

import org.springframework.context.ApplicationEvent;

public class BatchMsgEvent extends ApplicationEvent {  
	   
    private static final long serialVersionUID = 1L;  
   
    public BatchMsgEvent(Object source) {  
        super(source);  
    }  
    
} 