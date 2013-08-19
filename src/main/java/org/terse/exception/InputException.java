package org.terse.exception;

public class InputException extends BaseException{

	private static final long serialVersionUID = -7253415064583541138L;
	
	public InputException(int errCode) {
		super(errCode);
	}

}
