package com.opms.batch.dwr;

import java.util.Date;

public class BatchMsg {  
	
    private int id;  
    private Object msg;  
    private Date time;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}  

}