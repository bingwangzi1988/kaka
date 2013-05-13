package net.neptune.batch.core.invoker;

import java.util.ArrayList;

import javax.jws.WebService;

@WebService
public interface ContextService {

	public ArrayList<String> getStepExecutors();
	
	public void reloadJobGrpCfg();
	
}
