package org.eredlab.g4.ccl.tplengine.velocity;

import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * ��ʼģ�������쳣��
 * @author XiongChun
 * @since 2009-07-28
 * @see RuntimeException
 */
public class InitVelocityEngineException extends RuntimeException{
	
	/**
	 * ȱʡ���а汾��ʶ
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ���캯��1
	 * @param 
	 */
	public InitVelocityEngineException(){
		super(GlobalConstants.Exception_Head + "��ʼ��eRedG4ƽ̨ȱʡģ������ʧ��.\n");
	}
	
	/**
	 * ���캯��2
	 * @param 
	 */
	public InitVelocityEngineException(String msg){
		super(GlobalConstants.Exception_Head + "��ʼ��eRedG4ƽ̨ȱʡģ������ʧ��\n" + msg);
	}

}
