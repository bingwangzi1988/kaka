package net.neptune.batch.core.invoker;

import java.util.HashMap;

import javax.jws.WebService;

@WebService
public interface BatchInvoker {
	/**
	 * 
	 * @param jobGrpName ����������
	 * @param paramsMap ���κ�key="BatchNo"
	 * @throws Exception
	 */
	public void invokeStart(String jobGrpName,HashMap<String, Object> paramsMap) throws Exception;
	
	/**
	 * 
	 * @param batchNo ���κ�
	 * @throws Exception
	 */
	public void invokeContinue(String batchNo) throws Exception;
	
}
