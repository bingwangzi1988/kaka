package com.opms.batch.dwr;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.springframework.context.ApplicationListener;

public class BatchMsgClient implements ApplicationListener<BatchMsgEvent> {

	public void onApplicationEvent(BatchMsgEvent event) {
		BatchMsg msg = (BatchMsg) event.getSource();
		ServerContext context = ServerContextFactory.get();
		if (context == null) {
			return;
		}
		final String log = (String) msg.getMsg();
//		String page = context.getContextPath() + "/jsp/batch/batchjobgrpexecution.jsp";
		String page = context.getContextPath() + "/jsp/batch/batchexecmonitor.jsp";
		Browser.withPage(page, new Runnable() {
			public void run() {
				ScriptSessions.addFunctionCall("addLog", log);
				ScriptSessions.addFunctionCall("reloadData");
			}
		});
	}
	
}