package org.terse.exception;

@SuppressWarnings("serial")
public abstract class BaseException extends Exception{
	
	protected String errorCode;
	
	protected String errorMessage;
	
	public BaseException(int errCode){
		super(loadMessage(errCode));
		this.errorMessage=loadMessage(errCode);
		this.errorCode=String.valueOf(errCode);
	}
	
	private static String loadMessage(int errCode){
		return "";
		//return String.valueOf(Context.getProperties("error").getProperty(String.valueOf(errCode)));
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
