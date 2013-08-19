package org.terse.exception;


public class BusinessException extends BaseException{
	
	private static final long serialVersionUID = 7775613971334130415L;
	
	public BusinessException(int errCode) {
		super(errCode);
	}

}
