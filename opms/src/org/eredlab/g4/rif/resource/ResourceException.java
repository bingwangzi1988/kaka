package org.eredlab.g4.rif.resource;

/**
 * ResourceException
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-11-20
 */
public class ResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceException() {
		super("×ÊÔ´Òì³£");
	}

	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceException(String message) {
		super(message);
	}

	public ResourceException(Throwable cause) {
		super(cause);
	}

}
