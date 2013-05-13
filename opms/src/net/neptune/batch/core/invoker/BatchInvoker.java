package net.neptune.batch.core.invoker;

import java.util.HashMap;

import javax.jws.WebService;

@WebService
public interface BatchInvoker {
	/**
	 * 
	 * @param jobGrpName 批量组名称
	 * @param paramsMap 批次号key="BatchNo"
	 * @throws Exception
	 */
	public void invokeStart(String jobGrpName,HashMap<String, Object> paramsMap) throws Exception;
	
	/**
	 * 
	 * @param batchNo 批次号
	 * @throws Exception
	 */
	public void invokeContinue(String batchNo) throws Exception;
	
}
