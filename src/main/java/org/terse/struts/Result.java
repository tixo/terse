package org.terse.struts;

public abstract class Result{

	public static final String TERSE_RESULT = "TERSE_RESULT";
	
	public static final String SUCCESS = "SUCCESS";

	public String getResult(){
		return TERSE_RESULT;
	}
	
}
