package com.opms.batch.dwr;

import org.eredlab.g4.bmf.util.SpringBeanLoader;

public class BatchService {  
    
    public static void sendMessage(BatchMsg msg) { 
    	SpringBeanLoader.publishEvent(new BatchMsgEvent(msg));  
    }  
    
} 