package org.terse.struts;

public abstract class Result{

	public static final String T_RESULT = "TResult";
	
	public static final String SUCCESS = "SUCCESS";

	public String getResult(){
		return T_RESULT;
	}
	
}
